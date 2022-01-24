package com.adwi.shoppe.repository

import com.adwi.kotlin.data.local.Review
import com.adwi.shoppe.CreateReviewMutation
import com.adwi.shoppe.DeleteReviewMutation
import com.adwi.shoppe.GetReviewQuery
import com.adwi.shoppe.ReviewsPagedByShopIdQuery
import com.adwi.shoppe.UpdateReviewMutation
import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.mapper.Reviews
import com.adwi.shoppe.data.local.mapper.toReview
import com.adwi.shoppe.data.local.mapper.toReviews
import com.adwi.shoppe.type.ReviewInput
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ApolloExperimental
class ReviewRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {

    suspend fun getReviewsPagedByShopId(id: String, page: Optional<Int>, size: Optional<Int>): Reviews? {
        val response = apolloClient.query(ReviewsPagedByShopIdQuery(id, page, size)).execute()
        return response.data?.reviewsPagedByShopId?.toReviews()
    }

    suspend fun getReview(reviewId: String): Review? {
        val response = apolloClient.query(GetReviewQuery(reviewId)).execute()
        return response.data?.getReview?.toReview()
    }

    suspend fun createReview(shopId: String, reviewInput: ReviewInput): Review? {
        val response = apolloClient.mutation(CreateReviewMutation(shopId, reviewInput)).execute()
        return response.data?.createReview?.toReview()
    }

    suspend fun updateReview(reviewId: String, reviewInput: ReviewInput): Review? {
        val response = apolloClient.mutation(UpdateReviewMutation(reviewId, reviewInput)).execute()
        return response.data?.updateReview?.toReview()
    }

    suspend fun deleteReview(reviewId: String): Boolean? {
        val response = apolloClient.mutation(DeleteReviewMutation(reviewId)).execute()
        return response.data?.deleteReview
    }
}