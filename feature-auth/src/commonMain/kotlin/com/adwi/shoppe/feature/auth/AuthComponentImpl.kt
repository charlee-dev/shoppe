package com.adwi.shoppe.feature.auth

import com.adwi.shoppe.feature.auth.AuthComponent.Event
import com.adwi.shoppe.feature.auth.AuthComponent.Model
import com.adwi.shoppe.feature.auth.store.AuthStore.Intent
import com.adwi.shoppe.feature.auth.store.AuthStore.Label
import com.adwi.shoppe.feature.auth.store.AuthStoreFactory
import com.adwi.shoppe.utils.getStore
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ExperimentalSettingsImplementation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.kodein.di.DirectDI
import org.kodein.di.DirectDIAware
import org.kodein.di.instance

@ExperimentalCoroutinesApi
@ApolloExperimental
@ExperimentalSettingsApi
@ExperimentalSettingsImplementation
internal class AuthComponentImpl(
    override val directDI: DirectDI,
    private val componentContext: ComponentContext,
) : AuthComponent, DirectDIAware, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        AuthStoreFactory(
            storeFactory = directDI.instance(),
            authRepository = directDI.instance(),
            prefs = directDI.instance()
        ).create()
    }

    override val model: Flow<Model> = store.states.map {
        Model(
            it.email,
            it.password,
            it.isLoading
        )
    }

    override val events: Flow<Event> = store.labels.map {
        when (it) {
            is Label.MessageReceived -> Event.MessageReceived(it.message)
        }
    }

    override fun onLoginChanged(email: String) {
        store.accept(Intent.ChangeEmail(email))
    }

    override fun onPasswordChanged(password: String) {
        store.accept(Intent.ChangePassword(password))
    }

    override fun onSignIn() {
        store.accept(Intent.SignIn)
    }

    override fun onSignUp() {
        store.accept(Intent.SignUp)
    }
}