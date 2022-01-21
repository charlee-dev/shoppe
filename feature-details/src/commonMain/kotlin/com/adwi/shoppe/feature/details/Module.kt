package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.ComponentContext
import org.kodein.di.DI
import org.kodein.di.bindFactory

val detailsComponentModule = DI.Module("detailsComponent") {

    bindFactory<ComponentContext, DetailsComponent> { componentContext ->
        DetailsComponentImpl(di, componentContext)
    }
}