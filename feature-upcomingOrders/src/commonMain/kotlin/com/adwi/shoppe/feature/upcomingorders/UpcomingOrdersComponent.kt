package com.adwi.shoppe.feature.upcomingorders

import kotlinx.coroutines.flow.Flow

interface UpcomingOrdersComponent {

    data class OrderItem(
        val id: String,
        val serviceName: String,
        val price: Double,
        val quantity: Double,
        val scheduledAt: Long,
    ) {
        fun totalPrice(): Double = price * quantity
    }

    data class OrdersModel(
        val orderItems: List<OrderItem> = emptyList(),
        val isRefreshing: Boolean = false,
    )

    val model: Flow<OrdersModel>

    fun onRefreshItems()
    fun onOrderClick(id: String)
}