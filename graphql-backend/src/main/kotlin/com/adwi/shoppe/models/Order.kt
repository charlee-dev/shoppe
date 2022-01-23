package com.adwi.shoppe.models

import java.time.LocalDateTime

data class Order(
    override val id: String,
    val userId: String,
    val shopId: String,
    val serviceId: String,
    val serviceName: String,
    val price: Double,
    var quantity: Double,
    var purchasedAt: String = LocalDateTime.now().toString(),
    var scheduledAt: String,
    var paid: Boolean,
) : Model

data class OrderInput(
    var quantity: Double,
    var scheduledAt: String,
    var paid: Boolean,
)

data class OrderPage(
    val results: List<Order>,
    val info: PagingInfo,
)
