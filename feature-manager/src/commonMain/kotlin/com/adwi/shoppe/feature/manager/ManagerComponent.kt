package com.adwi.shoppe.feature.manager

import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ManagerComponent {

    data class ShopItem(
        val id: String,
        val name: String,
        val description: String,
    )

    data class Model(
        val shopItems: Flow<List<ShopItem>> = flowOf(emptyList()),
        val isRefreshing: Boolean = false,
    )

    val model: Flow<Model>

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        object Manager : Child()
        data class PreviewShop(val component: ShopPreviewComponent) : Child()
    }

    fun onShopClick(id: String)
    fun deleteShop(id: String)
}