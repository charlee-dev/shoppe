package com.adwi.shoppe.feature.manager

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val managerComponentModule = DI.Module("managerComponent") {

    bindFactory<ComponentContext, ManagerComponent> { componentContext ->
        ManagerComponentImpl(di, componentContext)
    }
}