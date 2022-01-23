package com.adwi.shoppe.feature.dashboard.store

import com.adwi.shoppe.feature.dashboard.DashboardComponent.ShopItem
import com.adwi.shoppe.feature.dashboard.store.DashboardStore.Intent
import com.adwi.shoppe.feature.dashboard.store.DashboardStore.State
import com.arkivanov.mvikotlin.core.store.Store

internal interface DashboardStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        object RefreshItems : Intent()
        data class ClickShop(val id: String) : Intent()
    }

    sealed class Result {
        data class ItemsReceived(val items: List<ShopItem>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    data class State(
        val items: List<ShopItem> = listOf(),
        val isRefreshing: Boolean = false,
        val isMoreLoading: Boolean = false,
    )
}