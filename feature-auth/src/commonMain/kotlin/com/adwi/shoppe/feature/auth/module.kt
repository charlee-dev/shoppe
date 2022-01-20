package com.adwi.shoppe.feature.auth

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory
import org.kodein.di.direct

@ExperimentalCoroutinesApi
@ApolloExperimental
@ExperimentalSettingsApi
@ExperimentalSettingsImplementation
val authComponentModule = DI.Module("authComponent") {

    bindFactory<ComponentContext, AuthComponent> { componentContext ->
        AuthComponentImpl(di.direct, componentContext)
    }
}