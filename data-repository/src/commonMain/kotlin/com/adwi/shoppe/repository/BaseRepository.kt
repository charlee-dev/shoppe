package com.adwi.shoppe.repository

import com.adwi.shoppe.data.api.ApolloProvider
import com.adwi.shoppe.data.local.Database
import com.apollographql.apollo3.ApolloClient

open class BaseRepository(apolloProvider: ApolloProvider) {
    val apolloClient: ApolloClient = apolloProvider.apolloClient
    val database: Database = apolloProvider.database
}