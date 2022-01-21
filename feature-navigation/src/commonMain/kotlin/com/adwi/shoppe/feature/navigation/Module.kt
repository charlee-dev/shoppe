package com.adwi.shoppe.feature.navigation

import com.adwi.shoppe.feature.dashboard.dashboardComponentModule
import com.adwi.shoppe.feature.manager.managerComponentModule
import com.adwi.shoppe.feature.planner.plannerComponentModule
import com.adwi.shoppe.feature.settings.settingsComponentModule
import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val navigationComponentModule = DI.Module("navigationComponent") {

    import(dashboardComponentModule)
    import(managerComponentModule)
    import(plannerComponentModule)
    import(settingsComponentModule)

    bindFactory<ComponentContext, NavigationComponent> { componentContext ->
        NavigationComponentImpl(di, componentContext)
    }
}