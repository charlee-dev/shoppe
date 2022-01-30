package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

data class ShopPreviewComponentParams(
    val componentContext: ComponentContext,
    val shopId: String,
    val onSaveClick: (Boolean) -> Unit,
    val navigateBack: () -> Unit,
)

@ExperimentalCoroutinesApi
val shopPreviewComponentModule = DI.Module("shopPreviewComponent") {

    bindFactory<ShopPreviewComponentParams, ShopPreviewComponent> { params ->
        ShopPreviewComponentImpl(
            di = di,
            componentContext = params.componentContext,
            shopId = params.shopId,
            onSaveClick = params.onSaveClick,
            navigateBack = params.navigateBack
        )
    }
}