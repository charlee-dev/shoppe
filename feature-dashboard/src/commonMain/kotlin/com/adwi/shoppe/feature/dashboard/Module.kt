package com.adwi.shoppe.feature.dashboard

import com.adwi.shoppe.feature.details.shopPreviewComponentModule
import com.adwi.shoppe.feature.shops.shopsComponentModule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

@ExperimentalCoroutinesApi
@ApolloExperimental
@ExperimentalSettingsApi
@ExperimentalSettingsImplementation
val dashboardComponentModule = DI.Module("dashboardComponent") {

    import(shopPreviewComponentModule)
    import(shopsComponentModule)

    bindFactory<ComponentContext, DashboardComponent> { componentContext ->
        DashboardComponentImpl(di, componentContext)
    }
}