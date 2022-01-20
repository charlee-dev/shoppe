package com.adwi.shoppe.services

import com.adwi.shoppe.models.Service
import com.adwi.shoppe.models.ServiceInput
import com.adwi.shoppe.models.ServicePage
import com.adwi.shoppe.repository.ServiceRepository
import java.util.*

class ServiceService(private val repository: ServiceRepository) {

    fun getService(id: String): Service {
        return repository.getById(id)
    }

    fun getServicesPageByShopId(
        shopId: String,
        page: Int,
        size: Int,
    ): ServicePage {
        return repository.getServicePageByShopId(
            shopId,
            page,
            size)
    }

    fun createService(shopId: String, serviceInput: ServiceInput): Service {
        val uid = UUID.randomUUID().toString()
        val review = Service(
            id = uid,
            shopId = shopId,
            name = serviceInput.name,
            description = serviceInput.description,
            price = serviceInput.price,
            duration = serviceInput.duration,
        )
        return repository.add(review)
    }

    fun updateService(serviceId: String, shopId: String, serviceInput: ServiceInput): Service {
        val updates = Service(
            id = serviceId,
            shopId = shopId,
            name = serviceInput.name,
            description = serviceInput.description,
            price = serviceInput.price,
            duration = serviceInput.duration,
        )
        return repository.update(updates)
    }

    fun deleteService(serviceId: String): Boolean {
        return repository.delete(serviceId)
    }
}