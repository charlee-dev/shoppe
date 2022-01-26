package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import org.kodein.di.DI
import org.kodein.di.DIAware

class ShopPreviewComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : ShopPreviewComponent, DIAware, ComponentContext by componentContext {

    override val model: Flow<ShopPreviewComponent.Model>
        get() = TODO("Not yet implemented")

    override fun onSaveClick() {
        TODO("Not yet implemented")
    }

}