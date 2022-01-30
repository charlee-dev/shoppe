package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow

interface ShopPreviewComponent {

    val routerState: Value<RouterState<*, Child>>

    data class ShopItem(
        val id: String = "",
        var name: String = "",
        var description: String = "",
        var imageUrl: String = "",
    )

    data class Model(
        val shop: ShopItem = ShopItem(),
        val isRefreshing: Boolean = false,
        val isEditMode: Boolean = false,
    )

    val model: Flow<Model>

    fun saveEdit(id: String, name: String, description: String, imageUrl: String)
    fun saveAdd(name: String, description: String, imageUrl: String)

    sealed class Child {
        object Preview : Child()
        data class Edit(val shopId: String) : Child()
        data class Add(val shopId: String) : Child()
    }
}