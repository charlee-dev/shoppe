package com.adwi.shoppe.feature.root

import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface RootComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Library(val component: NavigationComponent) : Child()
        data class Auth(val component: AuthComponent) : Child()
    }

    fun onSignInUserInputReceived(email: String, password: String)
    fun onSignOutUserInputReceived(email: String, password: String)
}