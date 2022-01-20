package com.adwi.shoppe.models

data class Review(
    override val id: String,
    val userId: String,
    val shopId: String,
    val text: String,
    val rating: Int,
) : Model

data class ReviewInput(val text: String, val rating: Int)

data class ReviewPage(
    val results: List<Review>,
    val info: PagingInfo,
)