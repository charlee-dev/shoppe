package com.adwi.shoppe

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.adwi.shoppe.feature.root.RootComponent
import com.adwi.shoppe.feature.root.rootComponentModule
import com.adwi.shoppe.ui.compose.RootContent
import com.adwi.shoppe.ui.compose.ShoppeTheme
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.factory

@ApolloExperimental
@ExperimentalCoroutinesApi
@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun main() = application {

    val di = DI { import(rootComponentModule) }
    val root by di.factory<ComponentContext, RootComponent>()

    val windowState = rememberWindowState()
    windowState.apply {
        size = DpSize(1200.dp, 800.dp)
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = "Shoppe"
    ) {
        ShoppeTheme(darkTheme = false) {
            RootContent(
                di = di,
                component = root(DefaultComponentContext(LifecycleRegistry())),
                windowWidth = windowState.size.width
            )
        }
    }
}