package com.adwi.shoppe.feature.upcomingorders.store

import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent.OrderItem
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore.Intent
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore.Result
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore.State
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.mpp.currentTimeMillis
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ApolloExperimental
@ExperimentalCoroutinesApi
internal class UpcomingOrdersStoreFactory(
    private val storeFactory: StoreFactory,
    private val shopRepository: ShopRepository,
    private val onOrderClick: (String) -> Unit,
) {
    fun create(): UpcomingOrdersStore =
        object : UpcomingOrdersStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "${UpcomingOrdersStore::class.simpleName}",
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
                        getOrderItems().collect {
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
                Intent.RefreshItems -> scope.launch { refreshOrders() }
                is Intent.ClickOrder -> onOrderClick(intent.id)
            }
        }

        private suspend fun getOrderItems(): Flow<List<OrderItem>> = flow {
            val shops = shopRepository.getProfileShops()
            val upcomingOrders = mutableListOf<OrderItem>()
            shops.map { shopDetail ->
                shopDetail.orders.map {
                    if (it.scheduledAt > currentTimeMillis()) {
                        upcomingOrders += OrderItem(
                            id = it.id,
                            serviceName = it.serviceName,
                            price = it.price.toDouble(),
                            quantity = it.quantity,
                            scheduledAt = it.scheduledAt
                        )
                    }
                }
            }
            upcomingOrders.sortBy { it.scheduledAt }
            emit(upcomingOrders.toList())
        }

        private suspend fun refreshOrders() {
            withContext(AppCoroutineDispatcher.IO) {
                getOrderItems()
            }
        }
    }

    private object ReducerImpl : Reducer<State, Result> {

        override fun State.reduce(msg: Result): State = when (msg) {
            is Result.ItemsReceived -> copy(items = msg.items)
            is Result.Loading -> copy(
                isRefreshing = msg.isLoading,
                isMoreLoading = msg.isLoading
            )
        }
    }
}
