package com.adwi.shoppe.plugins

import com.adwi.shoppe.graphql.authSchema
import com.adwi.shoppe.graphql.orderSchema
import com.adwi.shoppe.graphql.profileSchema
import com.adwi.shoppe.graphql.reviewSchema
import com.adwi.shoppe.graphql.serviceSchema
import com.adwi.shoppe.graphql.shopSchema
import com.adwi.shoppe.services.AuthService
import com.adwi.shoppe.services.OrderService
import com.adwi.shoppe.services.ProfileService
import com.adwi.shoppe.services.ReviewService
import com.adwi.shoppe.services.ServiceService
import com.adwi.shoppe.services.ShopService
import com.apurebase.kgraphql.GraphQL
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.application.log
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

fun Application.configureGraphQL() {
    install(GraphQL) {
        val di = closestDI()
        val authService by di.instance<AuthService>()
        val shopService by di.instance<ShopService>()
        val orderService by di.instance<OrderService>()
        val reviewService by di.instance<ReviewService>()
        val profileService by di.instance<ProfileService>()
        val serviceService by di.instance<ServiceService>()

        playground = true

        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            authSchema(authService)
            shopSchema(shopService)
            orderSchema(orderService)
            reviewSchema(reviewService)
            profileSchema(profileService)
            serviceSchema(serviceService)
        }
    }
}