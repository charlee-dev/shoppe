package com.adwi.shoppe.feature.upcomingorders

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

data class UpcomingOrdersComponentParams(
    val componentContext: ComponentContext,
    val onOrderClick: (String) -> Unit,
)

@ApolloExperimental
@ExperimentalCoroutinesApi
val upcomingOrdersComponentModule = DI.Module("upcomingOrders") {

    bindFactory<UpcomingOrdersComponentParams, UpcomingOrdersComponent> { params ->
        UpcomingOrdersComponentImpl(di, params.componentContext, params.onOrderClick)
    }
}