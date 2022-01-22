package com.adwi.shoppe.feature.splash

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DirectDI
import org.kodein.di.DirectDIAware

class SplashComponentImpl(
    override val directDI: DirectDI,
    componentContext: ComponentContext,
) : SplashComponent, DirectDIAware, ComponentContext by componentContext {

}