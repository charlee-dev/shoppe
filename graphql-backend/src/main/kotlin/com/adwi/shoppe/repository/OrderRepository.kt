package com.adwi.shoppe.repository

import com.adwi.shoppe.models.Order
import com.adwi.shoppe.models.OrderPage
import com.adwi.shoppe.models.PagingInfo
import com.adwi.shoppe.util.Constants.DATABASE_NAME
import com.adwi.shoppe.util.Constants.ORDER_COLLECTION
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class OrderRepository(client: MongoClient) : RepositoryInterface<Order> {

    override lateinit var col: MongoCollection<Order>

    init {
        val database = client.getDatabase(DATABASE_NAME)
        col = database.getCollection<Order>(ORDER_COLLECTION)
    }

    fun getOrdersByShopId(shopId: String): List<Order> {
        return try {
            val res = col.find(Order::shopId eq shopId)
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find order")
        }
    }

    fun getOrdersByUserId(userId: String): List<Order> {
        return try {
            val res = col.find(Order::userId eq userId)
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find order")
        }
    }

    fun getOrdersByServiceId(serviceId: String): List<Order> {
        return try {
            val res = col.find(Order::serviceId eq serviceId)
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find order")
        }
    }

    fun getOrdersPageByUserId(userId: String, page: Int, size: Int): OrderPage {
        try {
            val skips = page * size
            val res = col.find(Order::userId eq userId).skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val total = col.countDocuments(Order::userId eq userId)
            val totalPages = (total / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(total.toInt(), totalPages.toInt(), next, prev)
            return OrderPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get reviews page")
        }
    }

    fun getOrdersPageByShopId(shopId: String, page: Int, size: Int): OrderPage {
        try {
            val skips = page * size
            val res = col.find(Order::shopId eq shopId).skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val total = col.countDocuments(Order::shopId eq shopId)
            val totalPages = (total / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(total.toInt(), totalPages.toInt(), next, prev)
            return OrderPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get orders page")
        }
    }
}