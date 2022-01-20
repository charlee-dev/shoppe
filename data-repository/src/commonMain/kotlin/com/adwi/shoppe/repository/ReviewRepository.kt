package com.adwi.shoppe.repository

import com.adwi.kotlin.data.local.Review
import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.mapper.toReview
import com.adwi.shoppe.data.remote.CreateReviewMutation
import com.adwi.shoppe.data.remote.DeleteReviewMutation
import com.adwi.shoppe.data.remote.GetReviewQuery
import com.adwi.shoppe.data.remote.UpdateReviewMutation
import com.adwi.shoppe.data.remote.type.ReviewInput
import com.apollographql.apollo3.annotations.ApolloExperimental
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ApolloExperimental
class ReviewRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {
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