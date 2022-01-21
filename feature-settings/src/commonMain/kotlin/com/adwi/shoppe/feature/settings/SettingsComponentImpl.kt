package com.adwi.shoppe.feature.settings

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import org.kodein.di.DI
import org.kodein.di.DIAware

class SettingsComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : SettingsComponent, DIAware, ComponentContext by componentContext {

    override val routerState: Value<RouterState<*, SettingsComponent.Child>>
        get() = TODO("Not yet implemented")

    override fun onData() {
        TODO("Not yet implemented")
    }
}