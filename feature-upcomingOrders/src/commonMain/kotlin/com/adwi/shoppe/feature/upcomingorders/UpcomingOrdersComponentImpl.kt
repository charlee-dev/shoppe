package com.adwi.shoppe.feature.upcomingorders

import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent.OrdersModel
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStoreFactory
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

@ExperimentalCoroutinesApi
@ApolloExperimental
class UpcomingOrdersComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
    onOrderClick: (String) -> Unit,
) : UpcomingOrdersComponent, DIAware, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        UpcomingOrdersStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            onOrderClick = { onOrderClick(it) }
        ).create()
    }

    override val model: Flow<OrdersModel> = store.states.map {
        OrdersModel(
            orderItems = it.items,
            isRefreshing = it.isRefreshing
        )
    }

    override fun onRefreshItems() {
        store.accept(UpcomingOrdersStore.Intent.RefreshItems)
    }

    override fun onOrderClick(id: String) {
        onOrderClick(id)
    }
}