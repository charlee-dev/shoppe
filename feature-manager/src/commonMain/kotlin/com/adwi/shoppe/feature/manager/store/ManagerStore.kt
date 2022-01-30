package com.adwi.shoppe.feature.manager.store

import com.adwi.shoppe.feature.manager.ManagerComponent
import com.arkivanov.mvikotlin.core.store.Store

interface ManagerStore : Store<ManagerStore.Intent, ManagerStore.State, Nothing> {

    sealed class Intent {
        object RefreshItems : Intent()
        data class ClickShop(val id: String) : Intent()
        data class DeleteShop(val id: String) : Intent()
    }

    sealed class Result {
        data class ItemsReceived(val items: List<ManagerComponent.ShopItem>) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    data class State(
        val items: List<ManagerComponent.ShopItem> = listOf(),
        val isRefreshing: Boolean = false,
    )
}