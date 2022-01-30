package com.adwi.shoppe.feature.navigation

import com.adwi.shoppe.feature.dashboard.dashboardComponentModule
import com.adwi.shoppe.feature.manager.managerComponentModule
import com.adwi.shoppe.feature.planner.plannerComponentModule
import com.adwi.shoppe.feature.settings.settingsComponentModule
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

@ExperimentalSettingsImplementation
@ExperimentalSettingsApi
@ApolloExperimental
@ExperimentalCoroutinesApi
val navigationComponentModule = DI.Module("navigationComponent") {

    import(dashboardComponentModule)
    import(managerComponentModule)
    import(plannerComponentModule)
    import(settingsComponentModule)

    bindFactory<ComponentContext, NavigationComponent> { componentContext ->
        NavigationComponentImpl(di, componentContext)
    }
}