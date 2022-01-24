package com.adwi.shoppe.di

import com.adwi.shoppe.repository.OrderRepository
import com.adwi.shoppe.repository.ReviewRepository
import com.adwi.shoppe.repository.ServiceRepository
import com.adwi.shoppe.repository.ShopRepository
import com.adwi.shoppe.repository.UserRepository
import com.adwi.shoppe.services.AuthService
import com.adwi.shoppe.services.OrderService
import com.adwi.shoppe.services.ProfileService
import com.adwi.shoppe.services.ReviewService
import com.adwi.shoppe.services.ServiceService
import com.adwi.shoppe.services.ShopService
import io.ktor.application.Application
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.ktor.di
import org.litote.kmongo.KMongo

val mongoKey = System.getenv("MONGO_URI") ?: ""

fun Application.configureKodein() = di {

    bindSingleton { KMongo.createClient(mongoKey) }

    bindSingleton { OrderRepository(instance()) }
    bindSingleton { ReviewRepository(instance()) }
    bindSingleton { ServiceRepository(instance()) }
    bindSingleton { ShopRepository(instance()) }
    bindSingleton { UserRepository(instance()) }

    bindSingleton { AuthService(instance()) }
    bindSingleton { OrderService(instance(), instance()) }
    bindSingleton { ProfileService(instance(), instance()) }
    bindSingleton { ReviewService(instance()) }
    bindSingleton { ServiceService(instance()) }
    bindSingleton { ShopService(instance(), instance(), instance(), instance()) }
}