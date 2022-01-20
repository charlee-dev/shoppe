package com.adwi.shoppe.services

import com.adwi.shoppe.models.Review
import com.adwi.shoppe.models.ReviewInput
import com.adwi.shoppe.models.ReviewPage
import com.adwi.shoppe.repository.ReviewRepository
import java.util.*

class ReviewService(private val repository: ReviewRepository) {

    fun getReview(id: String): Review {
        return repository.getById(id)
    }

    fun getReviewsPageByShopId(shopId: String, page: Int, size: Int): ReviewPage {
        return repository.getReviewsPageByShopId(shopId, page, size)
    }

    fun createReview(userId: String, shopId: String, reviewInput: ReviewInput): Review {
        val uid = UUID.randomUUID().toString()
        val review = Review(
            id = uid,
            userId = userId,
            shopId = shopId,
            text = reviewInput.text,
            rating = reviewInput.rating
        )
        return repository.add(review)
    }

    fun updateReview(userId: String, reviewId: String, reviewInput: ReviewInput): Review {
        val review = repository.getById(reviewId)
        if (review.userId == userId) {
            val updates = Review(
                id = reviewId,
                shopId = review.shopId,
                userId = userId,
                text = reviewInput.text,
                rating = reviewInput.rating
            )
            return repository.update(updates)
        }
        error("Cannot update review")
    }

    fun deleteReview(userId: String, reviewId: String): Boolean {
        val review = repository.getById(reviewId)
        if (review.userId == userId) {
            return repository.delete(reviewId)
        }
        error("Cannot delete review")
    }
}