package com.adwi.shoppe.feature.root

import co.touchlab.kermit.Logger
import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent
import com.adwi.shoppe.feature.root.RootComponent.Child
import com.adwi.shoppe.feature.root.store.RootStore
import com.adwi.shoppe.feature.root.store.RootStoreFactory
import com.adwi.shoppe.feature.splash.SplashComponent
import com.adwi.shoppe.utils.getStore
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.replaceCurrent
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
internal class RootComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : RootComponent, DIAware, ComponentContext by componentContext {

    private val splash by factory<ComponentContext, SplashComponent>()
    private val library by factory<ComponentContext, NavigationComponent>()
    private val auth by factory<ComponentContext, AuthComponent>()

    private val router = router<Config, Child>(
        initialConfiguration = Config.Splash,
        handleBackButton = true
    ) { configuration, componentContext ->
        Logger.v("router $configuration")
        when (configuration) {
            is Config.Splash -> Child.Splash(splash(componentContext))
            is Config.Library -> Child.Library(library(componentContext))
            is Config.Auth -> Child.Auth(auth(componentContext))
        }
    }

    override val routerState: Value<RouterState<*, Child>> = router.state

    // Keeps root state and tracks Auth app state
    private val store = instanceKeeper.getStore {
        RootStoreFactory(
            storeFactory = direct.instance(),
            prefsStore = direct.instance(),
            authRepository = direct.instance(),
            onAuthenticationChanged = { isActive ->
                Logger.v("store = isSignedIn is $isActive")
                if (isActive)
                    setConfig<Child.Library>(Config.Library)
                else
                    setConfig<Child.Auth>(Config.Auth)
            }
        ).create()
    }

    init {
        checkUserState()
    }

    override fun checkUserState() {
        store.accept(RootStore.Intent.CheckUserState)
    }

    // Possible two routes in Root are Login screen or Library screen when signed in
    private sealed class Config : Parcelable {
        @Parcelize
        object Splash : Config()
        @Parcelize
        object Library : Config()
        @Parcelize
        object Auth : Config()
    }

    private inline fun <reified T : Child> setConfig(
        config: Config,
    ) {
        with(router) {
            if (state.value.activeChild.instance !is T) {
                replaceCurrent(config)
            }
        }
    }
}