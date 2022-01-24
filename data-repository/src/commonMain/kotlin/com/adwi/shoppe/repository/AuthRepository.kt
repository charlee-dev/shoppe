package com.adwi.shoppe.repository

import com.adwi.kotlin.data.local.UserState
import com.adwi.shoppe.SignInMutation
import com.adwi.shoppe.SignUpMutation
import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.type.UserInput
import com.apollographql.apollo3.annotations.ApolloExperimental
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Suppress("TooGenericExceptionThrown")
@ExperimentalCoroutinesApi
@ApolloExperimental
class AuthRepository(apolloProvider: ApolloProvider) : BaseRepository(apolloProvider) {

    suspend fun signIn(userInput: UserInput): String {
        val response = apolloClient.mutation(SignInMutation(userInput)).execute()
        response.data?.signIn?.let { data ->
            data.user.also {
                database.saveUserState(data.user.id, data.token)
            }
            return data.token
        }
        throw Exception("Could not sign in")
    }

    suspend fun signUp(userInput: UserInput): String {
        val response = apolloClient.mutation(SignUpMutation(userInput)).execute()
        response.data?.signUp?.let { data ->
            data.user.also {
                database.saveUserState(data.user.id, data.token)
            }
            return data.token
        }
        throw Exception("Could not sign up")
    }

    fun getUserState(): UserState? = database.getUserState()

    fun deleteUserState() {
        return database.deleteUserState()
    }
}