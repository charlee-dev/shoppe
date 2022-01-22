package com.adwi.shoppe.feature.root

import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent
import com.adwi.shoppe.feature.splash.SplashComponent
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Splash(val component: SplashComponent) : Child()
        data class Auth(val component: AuthComponent) : Child()
        data class Library(val component: NavigationComponent) : Child()
    }

    fun checkUserState()
}