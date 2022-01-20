package com.adwi.shoppe.repository

import com.adwi.shoppe.models.PagingInfo
import com.adwi.shoppe.models.Review
import com.adwi.shoppe.models.ReviewPage
import com.adwi.shoppe.util.Constants.DATABASE_NAME
import com.adwi.shoppe.util.Constants.REVIEW_COLLECTION
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

class ReviewRepository(client: MongoClient) : RepositoryInterface<Review> {

    override lateinit var col: MongoCollection<Review>

    init {
        val database = client.getDatabase(DATABASE_NAME)
        col = database.getCollection<Review>(REVIEW_COLLECTION)
    }

    fun getReviewsByShopId(shopId: String): List<Review> {
        return try {
            val res = col.find(Review::shopId eq shopId)
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }

    fun getReviewsPageByShopId(shopId: String, page: Int, size: Int): ReviewPage {
        try {
            val skips = page * size
            val res = col.find(Review::shopId eq shopId).skip(skips).limit(size)
            val results = res.asIterable().map { it }
            val total = col.countDocuments(Review::shopId eq shopId)
            val totalPages = (total / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(total.toInt(), totalPages.toInt(), next, prev)
            return ReviewPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get reviews page")
        }
    }
}