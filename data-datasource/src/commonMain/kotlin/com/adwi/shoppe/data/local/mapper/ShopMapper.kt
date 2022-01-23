package com.adwi.shoppe.data.local.mapper

import com.adwi.kotlin.data.local.Review
import com.adwi.kotlin.data.local.Service
import com.adwi.kotlin.data.local.Shop
import com.adwi.kotlin.data.local.ShopOrder
import com.adwi.shoppe.data.remote.CreateShopMutation
import com.adwi.shoppe.data.remote.GetProfileQuery
import com.adwi.shoppe.data.remote.GetShopByIdQuery
import com.adwi.shoppe.data.remote.ShopsPagedByUserIdQuery
import com.adwi.shoppe.data.remote.UpdateShopMutation

data class Shops(val results: List<Shop>, val info: ShopsPagedByUserIdQuery.Info?)
data class ShopDetail(
    val shop: Shop,
    val services: List<Service>,
    val reviews: List<Review>,
    val orders: List<ShopOrder>,
)

fun ShopsPagedByUserIdQuery.Result.toShop() = Shop(
    id = id,
    userId = userId,
    name = name,
    description = description,
    imageUrl = imageUrl
)

fun GetShopByIdQuery.Review.toReview() = Review(
    id = id,
    userId = userId,
    shopId = shopId,
    text = text,
    rating = rating.toLong()
)

fun ShopsPagedByUserIdQuery.ShopsPagedByUserId.toShops() = Shops(
    results = results.map { it.toShop() },
    info = info
)

fun GetShopByIdQuery.GetShopById.toShop() = Shop(
    id = id,
    userId = userId,
    name = name,
    description = description,
    imageUrl = imageUrl
)

fun GetShopByIdQuery.GetShopById.toShopDetail() =
    ShopDetail(
        shop = this.toShop(),
        services = services.map { it.toService() },
        reviews = reviews.map { it.toReview() },
        orders = orders.map { it.toShopOrder() }
    )

fun GetProfileQuery.Shop.toShop() = Shop(
    id = id,
    userId = userId,
    name = name,
    description = description,
    imageUrl = imageUrl
)

fun CreateShopMutation.CreateShop.toShop() = Shop(
    id = id,
    userId = userId,
    name = name,
    description = description,
    imageUrl = imageUrl
)

fun UpdateShopMutation.UpdateShop.toShop() = Shop(
    id = id,
    userId = userId,
    name = name,
    description = description,
    imageUrl = imageUrl
)