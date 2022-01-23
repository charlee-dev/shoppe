package com.adwi.shoppe.services

import com.adwi.shoppe.models.Order
import com.adwi.shoppe.models.OrderInput
import com.adwi.shoppe.models.OrderPage
import com.adwi.shoppe.repository.OrderRepository
import com.adwi.shoppe.repository.ServiceRepository
import java.util.*

class OrderService(
    private val orderRepository: OrderRepository,
    private val serviceRepository: ServiceRepository,
) {

    fun getOrder(id: String): Order {
        return orderRepository.getById(id)
    }

    fun getShopsPageByShopId(shopId: String, page: Int, size: Int): OrderPage {
        return orderRepository.getOrdersPageByShopId(shopId, page, size)
    }

    fun getShopsPageByUserId(userId: String, page: Int, size: Int): OrderPage {
        return orderRepository.getOrdersPageByUserId(userId, page, size)
    }

    fun createOrder(userId: String, shopId: String, serviceId: String, orderInput: OrderInput): Order {
        val uid = UUID.randomUUID().toString()
        val service = serviceRepository.getById(serviceId)
        val review = Order(
            id = uid,
            userId = userId,
            shopId = shopId,
            serviceId = serviceId,
            serviceName = service.name,
            price = service.price,
            quantity = orderInput.quantity,
            scheduledAt = orderInput.scheduledAt,
            paid = orderInput.paid
        )
        return orderRepository.add(review)
    }

    fun updateOrder(userId: String, orderId: String, shopId: String, serviceId: String, orderInput: OrderInput): Order {
        val review = orderRepository.getById(orderId)
        val service = serviceRepository.getById(serviceId)
        if (review.userId == userId) {
            val updates = Order(
                id = orderId,
                userId = userId,
                shopId = shopId,
                serviceId = serviceId,
                serviceName = service.name,
                price = service.price,
                quantity = orderInput.quantity,
                scheduledAt = orderInput.scheduledAt,
                paid = orderInput.paid
            )
            return orderRepository.update(updates)
        }
        error("Cannot update order")
    }

    fun deleteOrder(userId: String, orderId: String): Boolean {
        val review = orderRepository.getById(orderId)
        if (review.userId == userId) {
            return orderRepository.delete(orderId)
        }
        error("Cannot delete order")
    }
}