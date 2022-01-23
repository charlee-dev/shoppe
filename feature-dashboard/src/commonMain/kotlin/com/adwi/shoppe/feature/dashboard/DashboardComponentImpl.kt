package com.adwi.shoppe.feature.dashboard

import co.touchlab.kermit.Logger
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.feature.dashboard.DashboardComponent.Child
import com.adwi.shoppe.feature.dashboard.DashboardComponent.Model
import com.adwi.shoppe.feature.dashboard.store.DashboardStore.Intent
import com.adwi.shoppe.feature.dashboard.store.DashboardStoreFactory
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.repository.AuthRepository
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
class DashboardComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : DashboardComponent, DIAware, ComponentContext by componentContext {

    private val authRepository by di.instance<AuthRepository>()
    private val prefsStore by di.instance<PrefsStore>()

    val shopPreview = direct.factory<ComponentContext, ShopPreviewComponent>()

    private val router = router<Config, Child>(
        initialConfiguration = Config.None,
        handleBackButton = true
    ) { configuration, componentContext ->
        Logger.v("DashboardComponentImpl router $configuration")
        when (configuration) {
            is Config.None -> Child.Dashboard(this)
            is Config.ShopPreview -> Child.ShopDetails(shopPreview(componentContext))
        }
    }

    private val store = instanceKeeper.getStore {
        DashboardStoreFactory(
            storeFactory = direct.instance(),
            shopRepository = direct.instance(),
            onShopClick = { onShopClick(it) }
        ).create()
    }

    override val model: Flow<Model> = store.states.map {
        Model(
            shopItems = flowOf(it.items),
            isRefreshing = it.isRefreshing
        )
    }

    override val routerState: Value<RouterState<*, Child>> = router.state

    override fun onRefreshItems() {
        store.accept(Intent.RefreshItems)
    }

    override fun onShopClick(id: String) {
        setConfig<Child.ShopDetails>(Config.ShopPreview(id))
    }

    override fun signOut() {
        authRepository.deleteUserState()
        prefsStore.token = ""
        Logger.v("DashboardComponentImpl = signOut")
    }

    private inline fun <reified T : Child> setConfig(
        config: Config,
    ) {
        with(router) {
            if (state.value.activeChild.instance !is T) {
                push(config)
            }
        }
    }

    private sealed class Config : Parcelable {
        @Parcelize
        object None : Config()

        @Parcelize
        data class ShopPreview(val id: String) : Config()
    }
}