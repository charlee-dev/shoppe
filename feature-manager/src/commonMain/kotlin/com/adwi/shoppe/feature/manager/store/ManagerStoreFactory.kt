package com.adwi.shoppe.feature.manager.store

import com.adwi.shoppe.feature.manager.ManagerComponent.ShopItem
import com.adwi.shoppe.feature.manager.store.ManagerStore.Intent
import com.adwi.shoppe.feature.manager.store.ManagerStore.Result
import com.adwi.shoppe.feature.manager.store.ManagerStore.State
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ApolloExperimental
@ExperimentalCoroutinesApi
internal class ManagerStoreFactory(
    private val storeFactory: StoreFactory,
    private val shopRepository: ShopRepository,
    private val onShopClick: (String) -> Unit,
) {
    fun create(): ManagerStore = object : ManagerStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "${ManagerStore::class.simpleName}",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Result, Nothing>(
        AppCoroutineDispatcher.Main
    ) {
        override fun executeAction(
            action: Unit,
            getState: () -> State,
        ) {
            scope.launch {
                withContext(AppCoroutineDispatcher.Main) {
                    launch {
                        getShopItems().collect {
                            dispatch(Result.Loading(true))
                            dispatch(Result.ItemsReceived(it))
                            dispatch(Result.Loading(false))
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.RefreshItems -> scope.launch { refreshShops() }
                is Intent.ClickShop -> onShopClick(intent.id)
                is Intent.DeleteShop -> scope.launch {
                    shopRepository.deleteShop(intent.id)
                }
            }
        }

        private suspend fun getShopItems(): Flow<List<ShopItem>> = flowOf(
            shopRepository.getProfileShops().map { shop ->
                ShopItem(
                    id = shop.shop.id,
                    name = shop.shop.name,
                    description = shop.shop.description
                )
            }
        )

        private suspend fun refreshShops() {
            withContext(AppCoroutineDispatcher.IO) {
                getShopItems()
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {

        override fun State.reduce(msg: Result): State = when (msg) {
            is Result.ItemsReceived -> copy(items = msg.items)
            is Result.Loading -> copy(isRefreshing = msg.isLoading)
        }
    }
}