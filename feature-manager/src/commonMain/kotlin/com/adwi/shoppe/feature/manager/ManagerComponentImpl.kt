package com.adwi.shoppe.feature.manager

import co.touchlab.kermit.Logger
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.feature.details.ShopPreviewComponentParams
import com.adwi.shoppe.feature.manager.store.ManagerStore
import com.adwi.shoppe.feature.manager.store.ManagerStoreFactory
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
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

    private val shopPreview by factory<ShopPreviewComponentParams, ShopPreviewComponent>()

    private val router = router<Config, ManagerComponent.Child>(
        initialConfiguration = Config.Manager,
        handleBackButton = true
    ) { configuration, componentContext ->
        when (configuration) {
            is Config.Manager -> ManagerComponent.Child.Manager(this)
            is Config.ShopPreview -> ManagerComponent.Child.PreviewShop(
                component = shopPreview(
                    ShopPreviewComponentParams(
                        componentContext = componentContext,
                        shopId = configuration.shopId,
                        onSaveClick = { setConfig<ManagerComponent.Child.Manager>(Config.Manager) },
                        navigateBack = { setConfig<ManagerComponent.Child.Manager>(Config.Manager) }
                    )
                ),
                shopId = configuration.shopId
            )
        }
    }

    override val routerState: Value<RouterState<*, ManagerComponent.Child>> = router.state

    private val store = instanceKeeper.getStore {
        ManagerStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            onShopClick = { setConfig<ManagerComponent.Child.Manager>(Config.ShopPreview(it, true)) },
        ).create()
    }

    override val model: Flow<ManagerComponent.Model> = store.states.map {
        ManagerComponent.Model(
            shopItems = flowOf(it.items),
            isRefreshing = it.isRefreshing
        )
    }

    override fun onAddShopClick() {
        setConfig<ManagerComponent.Child.PreviewShop>(Config.ShopPreview("", false))
        Logger.v("onAddShopClick")
    }

    override fun onShopClick(id: String) {
        Logger.v("onShopClick = $id")
        setConfig<ManagerComponent.Child.PreviewShop>(Config.ShopPreview(id, true))
    }

    override fun deleteShop(id: String) {
        store.accept(ManagerStore.Intent.DeleteShop(id))
    }

    private inline fun <reified T : ManagerComponent.Child> setConfig(
        config: Config,
    ) {
        Logger.v("setConfig = $config")
        with(router) {
            if (state.value.activeChild.instance !is T) {
                push(config)
            }
        }
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object Manager : Config()

        @Parcelize
        data class ShopPreview(val shopId: String, val isPreview: Boolean) : Config()
    }
}