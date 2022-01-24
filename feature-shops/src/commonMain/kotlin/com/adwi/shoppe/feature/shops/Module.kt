package com.adwi.shoppe.feature.shops

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

data class ShopsComponentParams(
    val componentContext: ComponentContext,
    val onShopClick: (String) -> Unit,
)

@ApolloExperimental
@ExperimentalCoroutinesApi
val shopsComponentModule = DI.Module("shopsComponent") {

    bindFactory<ShopsComponentParams, ShopsComponent> { params ->
        ShopsComponentImpl(di, params.componentContext, params.onShopClick)
    }
}