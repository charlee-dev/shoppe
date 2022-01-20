package com.adwi.shoppe.repository

import com.adwi.shoppe.models.PagingInfo
import com.adwi.shoppe.models.Shop
import com.adwi.shoppe.models.ShopsPage
import com.adwi.shoppe.util.Constants.DATABASE_NAME
import com.adwi.shoppe.util.Constants.SHOP_COLLECTION
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ShopRepository(client: MongoClient) : RepositoryInterface<Shop> {

    override lateinit var col: MongoCollection<Shop>

    init {
        val database = client.getDatabase(DATABASE_NAME)
        col = database.getCollection<Shop>(SHOP_COLLECTION)
    }

    fun getShopsByUserId(userId: String): List<Shop> {
        return try {
            col.find(Shop::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user shops")
        }
    }

    fun getShopsPage(userId: String, page: Int, size: Int): ShopsPage {
        try {
            val skips = page * size
            val res = col.find(Shop::userId eq userId).skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val totalShops = col.countDocuments(Shop::userId eq userId)
            val totalPages = (totalShops / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalShops.toInt(), totalPages.toInt(), next, prev)
            return ShopsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get shops page")
        }
    }
}