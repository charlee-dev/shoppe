package com.adwi.shoppe.feature.splash

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.direct

val splashComponentModule = DI.Module("splashComponent") {

    bindFactory<ComponentContext, SplashComponent> { componentContext ->
        SplashComponentImpl(di.direct, componentContext)
    }
}