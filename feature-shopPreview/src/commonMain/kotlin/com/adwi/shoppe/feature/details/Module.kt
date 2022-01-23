package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val shopPreviewComponentModule = DI.Module("detailsComponent") {

    bindFactory<ComponentContext, ShopPreviewComponent> { componentContext ->
        ShopPreviewComponentImpl(di, componentContext)
    }
}