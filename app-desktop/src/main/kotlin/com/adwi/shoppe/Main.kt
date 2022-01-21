package com.adwi.shoppe

import androidx.compose.ui.window.singleWindowApplication
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
fun main() = singleWindowApplication(
    title = "Shoppe"
) {

    val di = DI { import(rootComponentModule) }
    val root by di.factory<ComponentContext, RootComponent>()

    ShoppeTheme {
        RootContent(
            di,
            root(DefaultComponentContext(LifecycleRegistry()))
        )
    }
}