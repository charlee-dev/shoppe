package com.adwi.shoppe.feature.shops

import com.adwi.shoppe.feature.shops.ShopsComponent.Model
import com.adwi.shoppe.feature.shops.store.ShopsStore.Intent
import com.adwi.shoppe.feature.shops.store.ShopsStoreFactory
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.instance

@ExperimentalCoroutinesApi
@ApolloExperimental
class ShopsComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
    onShopClick: (String) -> Unit,
) : ShopsComponent, DIAware, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ShopsStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            serviceRepository = direct.instance(),
            onShopClick = { onShopClick(it) }
        ).create()
    }

    override val model: Flow<Model> = store.states.map {
        Model(
            shopItems = flowOf(it.items),
            isRefreshing = it.isRefreshing
        )
    }

    override fun onRefreshItems() {
        store.accept(Intent.RefreshItems)
    }

    override fun onShopClick(id: String) {
        onShopClick(id)
    }
}