package com.adwi.shoppe.data.local.mapper

import com.adwi.kotlin.data.local.ShopOrder
import com.adwi.shoppe.data.remote.CreateOrderMutation
import com.adwi.shoppe.data.remote.GetOrderQuery
import com.adwi.shoppe.data.remote.GetShopByIdQuery
import com.adwi.shoppe.data.remote.OrdersPagedByShopIdQuery
import com.adwi.shoppe.data.remote.UpdateOrderMutation


fun GetOrderQuery.GetOrder.toShopOrder() = ShopOrder(
    id = id,
    userId = userId,
    shopId = shopId,
    serviceId = serviceId,
    quantity = quantity,
    purchasedAt = purchasedAt.toString().toDouble(),
    scheduledAt = scheduledAt.toString().toDouble(),
    paid = paid.toString().toLong()
)

fun OrdersPagedByShopIdQuery.Result.toShopOrder() = ShopOrder(
    id = id,
    userId = userId,
    shopId = shopId,
    serviceId = serviceId,
    quantity = quantity,
    purchasedAt = purchasedAt.toString().toDouble(),
    scheduledAt = scheduledAt.toString().toDouble(),
    paid = paid.toString().toLong()
)

fun CreateOrderMutation.CreateOrder.toShopOrder() = ShopOrder(
    id = id,
    userId = userId,
    shopId = shopId,
    serviceId = serviceId,
    quantity = quantity,
    purchasedAt = purchasedAt.toString().toDouble(),
    scheduledAt = scheduledAt.toString().toDouble(),
    paid = paid.toString().toLong()
)

fun UpdateOrderMutation.UpdateOrder.toShopOrder() = ShopOrder(
    id = id,
    userId = userId,
    shopId = shopId,
    serviceId = serviceId,
    quantity = quantity,
    purchasedAt = purchasedAt.toString().toDouble(),
    scheduledAt = scheduledAt.toString().toDouble(),
    paid = paid.toString().toLong()
)

fun GetShopByIdQuery.Order.toShopOrder() = ShopOrder(
    id = id,
    userId = userId,
    shopId = shopId,
    serviceId = serviceId,
    quantity = quantity,
    purchasedAt = purchasedAt.toString().toDouble(),
    scheduledAt = scheduledAt.toString().toDouble(),
    paid = paid.toString().toLong()
)