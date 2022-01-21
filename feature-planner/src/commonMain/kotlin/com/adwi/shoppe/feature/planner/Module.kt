package com.adwi.shoppe.feature.planner

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val plannerComponentModule = DI.Module("plannerComponent") {

    bindFactory<ComponentContext, PlannerComponent> { componentContext ->
        PlannerComponentImpl(di, componentContext)
    }
}