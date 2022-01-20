package com.adwi.shoppe.data.api

import com.adwi.shoppe.data.local.Database
import com.adwi.shoppe.data.local.DatabaseDriverFactory
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.BearerTokenInterceptor
import com.apollographql.apollo3.network.http.LoggingInterceptor
import com.apollographql.apollo3.network.http.TokenProvider

private val BASE_URL = "https://shoppe-graphql.herokuapp.com/graphql"

class ApolloProvider(databaseDriverFactory: DatabaseDriverFactory) : TokenProvider {

    val database = Database(databaseDriverFactory)
    val apolloClient: ApolloClient = ApolloClient.Builder()
        .serverUrl(BASE_URL)
        .addHttpHeader("Accept", "application/json")
        .addHttpHeader("Content-Type", "application/json")
        .addHttpInterceptor(BearerTokenInterceptor(this))
        .addHttpInterceptor(LoggingInterceptor())
        .build()

    override suspend fun currentToken(): String {
        return database.getUserState()?.token ?: ""
    }

    override suspend fun refreshToken(previousToken: String): String {
        return ""
    }
}