package com.adwi.shoppe.feature.details.store

import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.arkivanov.mvikotlin.core.store.Store

interface ShopPreviewStore : Store<ShopPreviewStore.Intent, ShopPreviewStore.State, Nothing> {

    sealed class Intent {
        data class CreateShop(val name: String, val description: String, val imageUrl: String) : Intent()
        data class UpdateShop(val id: String, val name: String, val description: String, val imageUrl: String) :
            Intent()

        object NavigateBack : Intent()
        data class SetName(val name: String) : Intent()
        data class SetDescription(val description: String) : Intent()
    }

    sealed class Result {
        data class ShopReceived(val shop: ShopPreviewComponent.ShopItem) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    data class State(
        val shop: ShopPreviewComponent.ShopItem = ShopPreviewComponent.ShopItem(),
        val isRefreshing: Boolean = false,
    )
}