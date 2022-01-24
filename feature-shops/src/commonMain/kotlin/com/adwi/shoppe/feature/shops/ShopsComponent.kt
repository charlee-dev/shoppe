package com.adwi.shoppe.feature.shops

import com.adwi.kotlin.data.local.Review
import com.adwi.kotlin.data.local.Service
import com.adwi.kotlin.data.local.ShopOrder
import com.apollographql.apollo3.mpp.currentTimeMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface ShopsComponent {

    data class ShopItem(
        val id: String,
        val name: String,
        val earnings: Int,
        val reviews: List<Review>,
        val orders: List<ShopOrder>,
        val services: List<Service>,
    ) {
        fun totalOrders(): Int = orders.size

        fun upcomingOrders(): Int = orders.filter { it.scheduledAt > currentTimeMillis() }.size

        fun averageRating() = if (reviews.isNotEmpty()) {
            reviews.sumOf { it.rating }.div(reviews.size).toDouble()
        } else 1.0
    }

    data class Model(
        val shopItems: Flow<List<ShopItem>> = flowOf(emptyList()),
        val isRefreshing: Boolean = false,
    )

    val model: Flow<Model>

    fun onRefreshItems()
    fun onShopClick(id: String)
}