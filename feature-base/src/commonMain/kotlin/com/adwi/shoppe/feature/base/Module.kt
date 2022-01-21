package com.adwi.shoppe.feature.base

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val baseComponentModule = DI.Module("baseComponent") {

    bindFactory<ComponentContext, BaseComponent> { componentContext ->
        BaseComponentImpl(di, componentContext)
    }
}