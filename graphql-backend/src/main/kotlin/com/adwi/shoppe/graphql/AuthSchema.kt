package com.adwi.shoppe.graphql

import com.adwi.shoppe.models.User
import com.adwi.shoppe.models.UserInput
import com.adwi.shoppe.services.AuthService
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder

fun SchemaBuilder.authSchema(authService: AuthService) {
    mutation("signIn") {
        description = "Authenticate an existing user"
        resolver { userInput: UserInput ->
            try {
                authService.signIn(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("signUp") {
        description = "Authenticate a new user"
        resolver { userInput: UserInput ->
            try {
                authService.signUp(userInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    type<User> {
        User::hashedPass.ignore()
    }
}