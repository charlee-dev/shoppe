package com.adwi.shoppe.graphql

import com.adwi.shoppe.models.Order
import com.adwi.shoppe.models.OrderInput
import com.adwi.shoppe.models.User
import com.adwi.shoppe.services.OrderService
import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder

fun SchemaBuilder.orderSchema(orderService: OrderService) {

    query("getOrder") {
        description = "Get an existing order"
        resolver { orderId: String ->
            try {
                orderService.getOrder(orderId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createOrder") {
        description = "Create a new order"
        resolver { shopId: String, serviceid: String, orderInput: OrderInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                orderService.createOrder(userId, shopId, serviceid, orderInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("ordersPagedByUserId") {
        description = "Retrieve orders page by userId"
        resolver { page: Int?, size: Int?, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                orderService.getShopsPageByUserId(userId, page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("ordersPagedByShopId") {
        description = "Retrieve orders page by shopId"
        resolver { shopId: String, page: Int?, size: Int? ->
            try {
                orderService.getShopsPageByShopId(shopId, page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateOrder") {
        description = "Update an existing order"
        resolver { orderId: String, shopId: String, serviceId: String, orderInput: OrderInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                orderService.updateOrder(userId, orderId, shopId, serviceId, orderInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteOrder") {
        description = "Delete a order"
        resolver { orderId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                orderService.deleteOrder(userId, orderId)
            } catch (e: Exception) {
                null
            }
        }
    }

    inputType<OrderInput> {
        description = "The input of the order without the identifier"
    }

    type<Order> {
        description = "Order object with the attributes text and rating"
    }
}