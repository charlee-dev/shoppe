package com.adwi.shoppe.services

import com.adwi.shoppe.models.Profile
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.repository.UserRepository

class ProfileService(
    private val userRepository: UserRepository,
    private val shopRepository: ShopRepository,
) {
    fun getProfile(userId: String): Profile {
        val user = userRepository.getById(userId)
        val shops = shopRepository.getShopsByUserId(userId)
        return Profile(user, shops)
    }
}