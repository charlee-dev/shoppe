package com.adwi.shoppe.feature.details.store

import com.adwi.shoppe.feature.details.ShopPreviewComponent.ShopItem
import com.adwi.shoppe.feature.details.store.ShopPreviewStore.Intent
import com.adwi.shoppe.feature.details.store.ShopPreviewStore.Result
import com.adwi.shoppe.feature.details.store.ShopPreviewStore.State
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.type.ShopInput
import com.adwi.shoppe.utils.AppCoroutineDispatcher
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ShopPreviewStoreFactory(
    private val storeFactory: StoreFactory,
    private val shopRepository: ShopRepository,
    private val shopId: String,
    private val onSaveClick: (Boolean) -> Unit,
    private val navigateBack: () -> Unit,
) {
    fun create(): ShopPreviewStore = object : ShopPreviewStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "${ShopPreviewStore::class.simpleName}",
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
                        getShopById(shopId).collect {
                            dispatch(Result.Loading(true))
                            dispatch(Result.ShopReceived(it))
                            dispatch(Result.Loading(false))
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.CreateShop -> scope.launch {
                    val newShop = shopRepository.createShop(ShopInput(
                        name = intent.name,
                        description = intent.description,
                        imageUrl = intent.imageUrl)
                    )
                    onSaveClick(newShop != null)
                }
                is Intent.UpdateShop -> scope.launch {
                    val updatedShop = shopRepository.updateShop(
                        shopId = shopId,
                        shopInput = ShopInput(
                            name = intent.name,
                            description = intent.description,
                            imageUrl = intent.imageUrl
                        )
                    )
                    onSaveClick(updatedShop != null)
                }
                Intent.NavigateBack -> navigateBack
                is Intent.SetName -> TODO()
                is Intent.SetDescription -> TODO()
            }
        }

        private suspend fun getShopById(shopId: String): Flow<ShopItem> = flow {
            val shopDetail = shopRepository.getShop(shopId)
            ShopItem(
                id = shopDetail?.shop?.id ?: "",
                name = shopDetail?.shop?.name ?: "",
                description = shopDetail?.shop?.description ?: ""
            )

        }
    }

    private object ReducerImpl : Reducer<State, Result> {

        override fun State.reduce(msg: Result): State = when (msg) {
            is Result.ShopReceived -> copy(shop = msg.shop)
            is Result.Loading -> copy(isRefreshing = msg.isLoading)
        }
    }
}