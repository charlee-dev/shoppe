package com.adwi.shoppe.services

import com.adwi.shoppe.models.Order
import com.adwi.shoppe.models.OrderInput
import com.adwi.shoppe.models.OrderPage
import com.adwi.shoppe.repository.OrderRepository
import java.util.*

class OrderService(private val repo: OrderRepository) {

    fun getOrder(id: String): Order {
        return repo.getById(id)
    }

    fun getShopsPageByShopId(shopId: String, page: Int, size: Int): OrderPage {
        return repo.getOrdersPageByShopId(shopId, page, size)
    }

    fun getShopsPageByUserId(userId: String, page: Int, size: Int): OrderPage {
        return repo.getOrdersPageByUserId(userId, page, size)
    }

    fun createOrder(userId: String, shopId: String, serviceId: String, orderInput: OrderInput): Order {
        val uid = UUID.randomUUID().toString()
        val review = Order(
            id = uid,
            userId = userId,
            shopId = shopId,
            serviceId = serviceId,
            quantity = orderInput.quantity,
            scheduledAt = orderInput.scheduledAt,
            paid = orderInput.paid
        )
        return repo.add(review)
    }

    fun updateOrder(userId: String, orderId: String, shopId: String, serviceId: String, orderInput: OrderInput): Order {
        val review = repo.getById(orderId)
        if (review.userId == userId) {
            val updates = Order(
                id = orderId,
                userId = userId,
                shopId = shopId,
                serviceId = serviceId,
                quantity = orderInput.quantity,
                scheduledAt = orderInput.scheduledAt,
                paid = orderInput.paid
            )
            return repo.update(updates)
        }
        error("Cannot update order")
    }

    fun deleteOrder(userId: String, orderId: String): Boolean {
        val review = repo.getById(orderId)
        if (review.userId == userId) {
            return repo.delete(orderId)
        }
        error("Cannot delete order")
    }
}