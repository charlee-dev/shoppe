package com.adwi.shoppe.data.local.mapper

import com.adwi.kotlin.data.local.Review
import com.adwi.shoppe.data.remote.CreateReviewMutation
import com.adwi.shoppe.data.remote.GetReviewQuery
import com.adwi.shoppe.data.remote.GetShopByIdQuery
import com.adwi.shoppe.data.remote.ReviewsPagedByShopIdQuery
import com.adwi.shoppe.data.remote.UpdateReviewMutation

fun GetShopByIdQuery.Review.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)

fun GetReviewQuery.GetReview.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)

fun ReviewsPagedByShopIdQuery.Result.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)

fun CreateReviewMutation.CreateReview.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)

fun UpdateReviewMutation.UpdateReview.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)