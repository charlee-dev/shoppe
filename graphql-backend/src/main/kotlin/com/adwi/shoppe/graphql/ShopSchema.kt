package com.adwi.shoppe.graphql

import com.adwi.shoppe.models.Shop
import com.adwi.shoppe.models.ShopInput
import com.adwi.shoppe.models.User
import com.adwi.shoppe.services.ShopService
import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder

fun SchemaBuilder.shopSchema(shopService: ShopService) {

    inputType<ShopInput> {
        description = "The input of the shop without the identifier"
    }

    type<Shop> {
        description = "Shop object with attributes name, description and imageUrl"
    }

    query("getShopById") {
        resolver { shopId: String ->
            try {
                shopService.getShop(shopId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("shopsPagedByUserId") {
        description = "Retrieve shops page"
        resolver { page: Int?, size: Int?, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                shopService.getShopsPage(userId, page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createShop") {
        description = "Create a new shop"
        resolver { shopInput: ShopInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                shopService.createShop(shopInput, userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateShop") {
        description = "Updates a shop"
        resolver { shopId: String, shopInput: ShopInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                shopService.updateShop(userId, shopId, shopInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteShop") {
        description = "Deletes a shop"
        resolver { shopId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                shopService.deleteShop(userId, shopId)
            } catch (e: Exception) {
                false
            }
        }
    }
}