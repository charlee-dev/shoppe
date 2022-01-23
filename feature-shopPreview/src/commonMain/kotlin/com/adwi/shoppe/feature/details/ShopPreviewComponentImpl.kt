package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import org.kodein.di.DI
import org.kodein.di.DIAware

class ShopPreviewComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : ShopPreviewComponent, DIAware, ComponentContext by componentContext {

    override val routerState: Value<RouterState<*, ShopPreviewComponent.Child>>
        get() = TODO("Not yet implemented")

    override fun onData() {
        TODO("Not yet implemented")
    }
}