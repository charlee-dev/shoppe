package com.adwi.shoppe.models

import java.util.Collections.emptyList

data class Shop(
    override val id: String,
    val userId: String,
    var name: String,
    var description: String,
    var imageUrl: String,
    var services: List<Service> = emptyList(),
    var reviews: List<Review> = emptyList(),
    var orders: List<Order> = emptyList(),
) : Model

data class ShopInput(
    val name: String,
    val description: String,
    val imageUrl: String,
)

data class ShopsPage(
    val results: List<Shop>,
    val info: PagingInfo,
)