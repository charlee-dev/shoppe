package com.adwi.shoppe.feature.manager

import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.feature.manager.store.ManagerStore
import com.adwi.shoppe.feature.manager.store.ManagerStoreFactory
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.direct
import org.kodein.di.factory
import org.kodein.di.instance

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ApolloExperimental
@ExperimentalCoroutinesApi
class ManagerComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : ManagerComponent, DIAware, ComponentContext by componentContext {

    private val shopPreview = direct.factory<ComponentContext, ShopPreviewComponent>()

    private val router = router<Config, ManagerComponent.Child>(
        initialConfiguration = Config.Manager,
        handleBackButton = true
    ) { configuration, componentContext ->
        when (configuration) {
            is Config.Manager -> ManagerComponent.Child.Manager(this)
            is Config.ShopPreview -> ManagerComponent.Child.PreviewShop(shopPreview(componentContext),
                configuration.shopId)
        }
    }

    override val routerState: Value<RouterState<*, ManagerComponent.Child>> = router.state

    private val store = instanceKeeper.getStore {
        ManagerStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            onShopClick = { setConfig<ManagerComponent.Child.PreviewShop>(Config.ShopPreview(it)) },
            onAddShopClick = { setConfig<ManagerComponent.Child.PreviewShop>(Config.ShopPreview("")) }
        ).create()
    }

    override val model: Flow<ManagerComponent.Model> = store.states.map {
        ManagerComponent.Model(
            shopItems = flowOf(it.items),
            isRefreshing = it.isRefreshing
        )
    }

    override fun onAddShopClick() {
        store.accept(ManagerStore.Intent.AddShop)
    }

    override fun onShopClick(id: String) {
        store.accept(ManagerStore.Intent.ClickShop(id))
    }

    override fun deleteShop(id: String) {
        store.accept(ManagerStore.Intent.DeleteShop(id))
    }

    private inline fun <reified T : ManagerComponent.Child> setConfig(
        config: Config,
    ) {
        with(router) {
            if (state.value.activeChild.instance !is T) {
                replaceCurrent(config)
            }
        }
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Manager : Config()

        @Parcelize
        data class ShopPreview(val shopId: String) : Config()
    }
}