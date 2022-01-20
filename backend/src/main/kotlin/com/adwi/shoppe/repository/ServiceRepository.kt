package com.adwi.shoppe.repository

import com.adwi.shoppe.models.PagingInfo
import com.adwi.shoppe.models.Service
import com.adwi.shoppe.models.ServicePage
import com.adwi.shoppe.util.Constants.DATABASE_NAME
import com.adwi.shoppe.util.Constants.SERVICE_COLLECTION
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ServiceRepository(client: MongoClient) : RepositoryInterface<Service> {

    override lateinit var col: MongoCollection<Service>

    init {
        val database = client.getDatabase(DATABASE_NAME)
        col = database.getCollection<Service>(SERVICE_COLLECTION)
    }

    fun getServicesByShopId(shopId: String): List<Service> {
        return try {
            val res = col.find(Service::shopId eq shopId)
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find service")
        }
    }

    fun getServicePageByShopId(
        shopId: String,
        page: Int,
        size: Int,
    ): ServicePage {
        try {
            val skips = page * size
            val res = col.find(Service::shopId eq shopId).skip(skips).limit(size)
            val results = res.asIterable().toList()
            val total = col.countDocuments(Service::shopId eq shopId)
            val totalPages = (total / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(total.toInt(), totalPages.toInt(), next, prev)
            return ServicePage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get services page")
        }
    }
}