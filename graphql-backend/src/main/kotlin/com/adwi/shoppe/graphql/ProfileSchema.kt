package com.adwi.shoppe.graphql

import com.adwi.shoppe.models.User
import com.adwi.shoppe.services.ProfileService
import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder

fun SchemaBuilder.profileSchema(profileService: ProfileService) {
    query("getProfile") {
        resolver { ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                profileService.getProfile(userId)
            } catch (e: Exception) {
                null
            }
        }
    }
}