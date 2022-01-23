package com.adwi.shoppe.feature.dashboard

import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface DashboardComponent {

    data class ShopItem(
        val id: String,
        val name: String,
        val rating: Int,
        val totalOrders: Int,
        val upcomingOrders: Int,
    )

    data class Model(
        val shopItems: Flow<List<ShopItem>> = flowOf(emptyList()),
        val isRefreshing: Boolean = false,
    )

    val model: Flow<Model>

    val routerState: Value<RouterState<*, Child>>

    fun onRefreshItems()
    fun onShopClick(id: String)
    fun signOut()

    sealed class Child() {
        data class Dashboard(val component: DashboardComponent) : Child()
        data class ShopDetails(val component: ShopPreviewComponent) : Child()
    }
}