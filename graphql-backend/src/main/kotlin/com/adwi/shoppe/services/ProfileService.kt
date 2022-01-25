package com.adwi.shoppe.services

import com.adwi.shoppe.models.Profile
import com.adwi.shoppe.repository.OrderRepository
import com.adwi.shoppe.repository.ReviewRepository
import com.adwi.shoppe.repository.ServiceRepository
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.repository.UserRepository

class ProfileService(
    private val userRepository: UserRepository,
    private val shopRepository: ShopRepository,
    private val reviewRepo: ReviewRepository,
    private val serviceRepo: ServiceRepository,
    private val orderRepository: OrderRepository,
) {
    fun getProfile(userId: String): Profile {
        val user = userRepository.getById(userId)
        val shops = shopRepository.getShopsByUserId(userId)
        shops.forEach { shop ->
            shop.reviews = reviewRepo.getReviewsByShopId(shop.id)
            shop.services = serviceRepo.getServicesByShopId(shop.id)
            shop.orders = orderRepository.getOrdersByShopId(shop.id)
        }
        return Profile(user, shops)
    }
}