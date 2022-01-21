package com.adwi.shoppe.feature.dashboard

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val dashboardComponentModule = DI.Module("dashboardComponent") {

    bindFactory<ComponentContext, DashboardComponent> { componentContext ->
        DashboardComponentImpl(di, componentContext)
    }
}