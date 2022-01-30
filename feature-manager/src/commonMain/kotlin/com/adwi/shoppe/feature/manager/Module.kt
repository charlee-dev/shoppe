package com.adwi.shoppe.feature.manager

import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.kodein.di.DI
import org.kodein.di.bindFactory

@ExperimentalCoroutinesApi
@ApolloExperimental
@ExperimentalSettingsApi
@ExperimentalSettingsImplementation
val managerComponentModule = DI.Module("managerComponent") {

    bindFactory<ComponentContext, ManagerComponent> { componentContext ->
        ManagerComponentImpl(di, componentContext)
    }
}