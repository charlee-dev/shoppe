package com.adwi.shoppe.feature.details

import kotlinx.coroutines.flow.Flow

interface ShopPreviewComponent {

    data class ShopItem(
        val id: String? = "",
        val name: String = "",
        val description: String = "",
    )

    data class Model(
        val shop: ShopItem = ShopItem(),
        val isLoading: Boolean = false,
        val isEditMode: Boolean = false,
    )

    val model: Flow<Model>

    sealed class Child {
        data class Preview(val shopId: String) : Child()
    }

    fun onSaveClick()
}