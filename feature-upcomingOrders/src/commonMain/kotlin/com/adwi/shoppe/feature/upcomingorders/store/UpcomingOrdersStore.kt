package com.adwi.shoppe.feature.upcomingorders.store

import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent.OrderItem
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore.Intent
import com.adwi.shoppe.feature.upcomingorders.store.UpcomingOrdersStore.State
import com.arkivanov.mvikotlin.core.store.Store

internal interface UpcomingOrdersStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        object RefreshItems : Intent()
        data class ClickOrder(val id: String) : Intent()
    }

    sealed class Result {
        data class ItemsReceived(val items: List<OrderItem>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    data class State(
        val items: List<OrderItem> = listOf(),
        val isRefreshing: Boolean = false,
        val isMoreLoading: Boolean = false,
    )
}