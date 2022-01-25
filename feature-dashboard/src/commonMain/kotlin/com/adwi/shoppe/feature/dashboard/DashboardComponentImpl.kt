package com.adwi.shoppe.feature.dashboard

import co.touchlab.kermit.Logger
import com.adwi.shoppe.data.sdk.prefs.PrefsStore
import com.adwi.shoppe.feature.dashboard.DashboardComponent.Child
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.feature.shops.ShopsComponent
import com.adwi.shoppe.feature.shops.ShopsComponentParams
import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent
import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponentParams
import com.adwi.shoppe.repository.AuthRepository
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    override val shops: ShopsComponent = direct.factory<ShopsComponentParams, ShopsComponent>()(
        ShopsComponentParams(
            componentContext = childContext("Shops"),
            onShopClick = { id -> setConfig<Child.ShopDetails>(Config.ShopPreview(id)) }
        )
    )

    override val upcomingOrders: UpcomingOrdersComponent =
        direct.factory<UpcomingOrdersComponentParams, UpcomingOrdersComponent>()(
            UpcomingOrdersComponentParams(
                componentContext = childContext("UpcomingOrders"),
                onOrderClick = { id -> setConfig<Child.ShopDetails>(Config.OrderPreview(id)) }
            )
        )

    private val shopPreview = direct.factory<ComponentContext, ShopPreviewComponent>()

    private val authRepository by di.instance<AuthRepository>()
    private val prefsStore by di.instance<PrefsStore>()

    private val router = router<Config, Child>(
        initialConfiguration = Config.None,
        handleBackButton = true
    ) { configuration, componentContext ->
        Logger.v("DashboardComponentImpl router $configuration")
        when (configuration) {
            is Config.None -> Child.Dashboard(this)
            is Config.ShopPreview -> Child.ShopDetails(shopPreview(componentContext))
            is Config.OrderPreview -> Child.OrderDetails(shopPreview(componentContext))
        }
    }

    override val routerState: Value<RouterState<*, Child>> = router.state

    override fun onOrderClick(id: String) {
        setConfig<Child.OrderDetails>(Config.OrderPreview(id))
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
        @Parcelize
        data class OrderPreview(val id: String) : Config()
    }
}