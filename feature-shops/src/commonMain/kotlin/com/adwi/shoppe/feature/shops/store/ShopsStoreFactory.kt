package com.adwi.shoppe.feature.shops.store

import co.touchlab.kermit.Logger
import com.adwi.shoppe.data.local.mapper.ShopDetail
import com.adwi.shoppe.feature.shops.ShopsComponent.ShopItem
import com.adwi.shoppe.feature.shops.store.ShopsStore.Intent
import com.adwi.shoppe.feature.shops.store.ShopsStore.Result
import com.adwi.shoppe.feature.shops.store.ShopsStore.State
import com.adwi.shoppe.repository.ServiceRepository
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
internal class ShopsStoreFactory(
    private val storeFactory: StoreFactory,
    private val shopRepository: ShopRepository,
    private val serviceRepository: ServiceRepository,
    private val onShopClick: (String) -> Unit,
) {
    fun create(): ShopsStore = object : ShopsStore, Store<Intent, State, Nothing> by storeFactory.create(
        name = "${ShopsStore::class.simpleName}",
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
            }
        }

        private suspend fun getShopItems(): Flow<List<ShopItem>> = flowOf(
            shopRepository.getProfileShops().map { shop ->
                Logger.v("getShopItems - shop = ${shop.name}")
                val shopDetail: ShopDetail? = shopRepository.getShop(shop.id)

                data class SimpleOrder(val price: Double, val quantity: Double)

                val simpleOrders = shopDetail?.orders?.map { order ->
                    val service = serviceRepository.getService(order.serviceId)

                    val simpleOrder = service?.let {
                        SimpleOrder(
                            price = it.price,
                            quantity = order.quantity
                        )
                    }
                    simpleOrder
                }

                val earnings: List<Int>? = simpleOrders?.map {
                    it?.price?.times(it.quantity)?.toInt() ?: 0
                }

                val item = ShopItem(
                    id = shop.id,
                    name = shop.name,
                    earnings = earnings?.sum() ?: 0,
                    reviews = shopDetail?.reviews ?: emptyList(),
                    orders = shopDetail?.orders ?: emptyList(),
                    services = shopDetail?.services ?: emptyList()
                )
                item
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
            is Result.Loading -> copy(
                isRefreshing = msg.isLoading,
                isMoreLoading = msg.isLoading
            )
        }
    }
}
