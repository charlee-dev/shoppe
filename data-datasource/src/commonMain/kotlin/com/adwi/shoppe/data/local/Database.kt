package com.adwi.shoppe.data.local

import com.adwi.kotlin.data.local.Review
import com.adwi.kotlin.data.local.Service
import com.adwi.kotlin.data.local.Shop
import com.adwi.kotlin.data.local.ShopOrder
import com.adwi.kotlin.data.local.UserState
import com.adwi.shoppe.data.ShoppeDatabase

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = ShoppeDatabase(databaseDriverFactory.create())

    private val shopQueries = database.shopQueries
    private val reviewQueries = database.reviewQueries
    private val serviceQueries = database.serviceQueries
    private val orderQueries = database.orderQueries
    private val userstateQueries = database.userstateQueries

    internal fun clearDatabase() {
        shopQueries.transaction {
            shopQueries.removeAllShops()
        }
    }

    // Shop
    fun getShops(): List<Shop> {
        return shopQueries.selectAllShops().executeAsList()
    }

    fun getShopById(shopId: String): Shop? {
        return shopQueries.selectShopById(shopId).executeAsOneOrNull()
    }

    fun saveShop(shop: Shop) {
        shopQueries.transaction {
            insertShop(shop)
        }
    }

    fun updateShop(shop: Shop) {
        shopQueries.transaction {
            updateShopById(shop)
        }
    }

    fun deleteShop(shopId: String) {
        shopQueries.transaction {
            removeShop(shopId)
        }
    }

    private fun removeShop(shopId: String) {
        shopQueries.removeShopById(shopId)
    }

    private fun insertShop(shop: Shop) {
        shopQueries.insertShop(
            shop.id,
            shop.userId,
            shop.name,
            shop.description,
            shop.imageUrl
        )
    }

    private fun updateShopById(shop: Shop) {
        shopQueries.updateShopById(
            name = shop.name,
            description = shop.description,
            imageUrl = shop.imageUrl,
            id = shop.id
        )
    }

    // Review
    internal fun getReviews(): List<Review> {
        return reviewQueries.selectAllReviews().executeAsList()
    }

    internal fun getReviewById(reviewId: String): Review? {
        return reviewQueries.selectReviewById(reviewId).executeAsOneOrNull()
    }

    internal fun saveReview(review: Review) {
        reviewQueries.transaction {
            insertReview(review)
        }
    }

    internal fun updateReview(review: Review) {
        reviewQueries.transaction {
            updateReviewById(review)
        }
    }

    internal fun deleteReview(reviewId: String) {
        reviewQueries.transaction {
            removeReview(reviewId)
        }
    }

    private fun removeReview(reviewId: String) {
        reviewQueries.removeReviewById(reviewId)
    }

    private fun insertReview(review: Review) {
        reviewQueries.insertReview(
            review.id,
            review.userId,
            review.shopId,
            review.text,
            review.rating
        )
    }

    private fun updateReviewById(review: Review) {
        reviewQueries.updateReviewById(
            text = review.text,
            rating = review.rating,
            id = review.id
        )
    }

    // Service
    internal fun getServices(): List<Service> {
        return serviceQueries.selectAllServices().executeAsList()
    }

    internal fun getServiceById(serviceId: String): Service? {
        return serviceQueries.selectServiceById(serviceId).executeAsOneOrNull()
    }

    internal fun saveService(service: Service) {
        serviceQueries.transaction {
            insertService(service)
        }
    }

    internal fun updateService(service: Service) {
        serviceQueries.transaction {
            updateServiceById(service)
        }
    }

    internal fun deleteService(serviceId: String) {
        serviceQueries.transaction {
            removeService(serviceId)
        }
    }

    private fun removeService(serviceId: String) {
        serviceQueries.removeServiceById(serviceId)
    }

    private fun insertService(service: Service) {
        serviceQueries.insertService(
            service.id,
            service.shopId,
            service.name,
            service.description,
            service.price,
            service.duration,
        )
    }

    private fun updateServiceById(service: Service) {
        serviceQueries.updateServiceById(
            id = service.id,
            name = service.name,
            description = service.description,
            duration = service.duration,
            price = service.price,
        )
    }

    // ShopOrder
    internal fun getShopOrders(): List<ShopOrder> {
        return orderQueries.selectAllShopOrders().executeAsList()
    }

    internal fun getShopOrderById(shopOrderId: String): ShopOrder? {
        return orderQueries.selectShopOrderById(shopOrderId).executeAsOneOrNull()
    }

    internal fun saveShopOrder(shopOrder: ShopOrder) {
        orderQueries.transaction {
            insertShopOrder(shopOrder)
        }
    }

    internal fun updateService(shopOrder: ShopOrder) {
        orderQueries.transaction {
            updateShopOrderById(shopOrder)
        }
    }

    internal fun deleteShopOrder(shopOrderId: String) {
        orderQueries.transaction {
            removeShopOrder(shopOrderId)
        }
    }

    private fun removeShopOrder(shopOrderId: String) {
        orderQueries.removeShopOrderById(shopOrderId)
    }

    private fun insertShopOrder(shopOrder: ShopOrder) {
        orderQueries.insertShopOrder(
            shopOrder.id,
            shopOrder.userId,
            shopOrder.shopId,
            shopOrder.serviceId,
            shopOrder.serviceName,
            shopOrder.price,
            shopOrder.quantity,
            shopOrder.purchasedAt,
            shopOrder.scheduledAt,
            shopOrder.paid,
        )
    }

    private fun updateShopOrderById(shopOrder: ShopOrder) {
        orderQueries.updateShopOrderById(
            id = shopOrder.id,
            quantity = shopOrder.quantity,
            purchasedAt = shopOrder.purchasedAt,
            scheduledAt = shopOrder.scheduledAt,
            paid = shopOrder.paid,
        )
    }

    // UserState
    fun getUserState(): UserState? {
        return userstateQueries.selectUserState().executeAsOneOrNull()
    }

    fun saveUserState(userId: String, token: String) {
        userstateQueries.transaction {
            insertUserState(userId, token)
        }
    }

    fun deleteUserState() {
        userstateQueries.transaction {
            removeUserState()
        }
    }

    private fun insertUserState(userId: String, token: String) {
        userstateQueries.insertUserState(userId, token)
    }

    private fun removeUserState() {
        userstateQueries.removeUserState()
    }
}