package com.adwi.shoppe.feature.shops.store

import com.adwi.shoppe.feature.shops.ShopsComponent
import com.arkivanov.mvikotlin.core.store.Store

internal interface ShopsStore : Store<ShopsStore.Intent, ShopsStore.State, Nothing> {

    sealed class Intent {
        object RefreshItems : Intent()
        data class ClickShop(val id: String) : Intent()
    }

    sealed class Result {
        data class ItemsReceived(val items: List<ShopsComponent.ShopItem>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    data class State(
        val items: List<ShopsComponent.ShopItem> = listOf(),
        val isRefreshing: Boolean = false,
        val isMoreLoading: Boolean = false,
    )
}