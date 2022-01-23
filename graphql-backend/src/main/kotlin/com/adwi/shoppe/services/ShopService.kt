package com.adwi.shoppe.services

import com.adwi.shoppe.models.Shop
import com.adwi.shoppe.models.ShopInput
import com.adwi.shoppe.models.ShopsPage
import com.adwi.shoppe.repository.OrderRepository
import com.adwi.shoppe.repository.ReviewRepository
import com.adwi.shoppe.repository.ServiceRepository
import com.adwi.shoppe.repository.ShopRepository
import java.util.*

class ShopService(
    private val repo: ShopRepository,
    private val reviewRepo: ReviewRepository,
    private val serviceRepo: ServiceRepository,
    private val orderRepository: OrderRepository,
) {
    fun getShop(id: String): Shop {
        val shop = repo.getById(id)
        shop.reviews = reviewRepo.getReviewsByShopId(id)
        shop.services = serviceRepo.getServicesByShopId(id)
        shop.orders = orderRepository.getOrdersByShopId(id)
        return shop
    }

    fun getShopsPage(userId: String, page: Int, size: Int): ShopsPage {
        return repo.getShopsPage(userId, page, size)
    }

    fun createShop(shopInput: ShopInput, userId: String): Shop {
        val uid = UUID.randomUUID().toString()
        val shop = Shop(
            id = uid,
            userId = userId,
            name = shopInput.name,
            description = shopInput.description,
            imageUrl = shopInput.imageUrl
        )
        return repo.add(shop)
    }

    fun updateShop(userId: String, shopId: String, shopInput: ShopInput): Shop {
        val shop = repo.getById(shopId)
        if (shop.userId == userId) {
            val updates = Shop(
                id = shopId,
                userId = userId,
                name = shopInput.name,
                description = shopInput.description,
                imageUrl = shopInput.imageUrl
            )
            return repo.update(updates)
        }
        error("Cannot update shop")
    }

    fun deleteShop(userId: String, shopId: String): Boolean {
        val shop = repo.getById(shopId)
        if (shop.userId == userId) {
            return repo.delete(shopId)
        }
        error("Cannot delete shop")
    }
}