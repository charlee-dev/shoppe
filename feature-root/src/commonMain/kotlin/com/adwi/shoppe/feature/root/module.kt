package com.adwi.shoppe.feature.root

import com.adwi.shoppe.data.sdk.sdkDataModule
import com.adwi.shoppe.feature.auth.authComponentModule
import com.adwi.shoppe.feature.navigation.navigationComponentModule
import com.adwi.shoppe.feature.splash.splashComponentModule
import com.adwi.shoppe.repositoryDataModule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.bindSingleton

@ApolloExperimental
@ExperimentalCoroutinesApi
@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
val rootComponentModule = DI.Module("rootComponent") {

    import(repositoryDataModule)
    importOnce(sdkDataModule)

    import(authComponentModule)
    import(splashComponentModule)
    import(navigationComponentModule)

    bindSingleton { DefaultStoreFactory() }

    bindFactory<ComponentContext, RootComponent> { componentContext ->
        RootComponentImpl(di, componentContext)
    }
}