package com.adwi.shoppe.feature.auth

import kotlinx.coroutines.flow.Flow

interface AuthComponent {

    data class Model(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    sealed class Event {
        data class MessageReceived(val message: String?) : Event()
    }

    val model: Flow<Model>

    val events: Flow<Event>

    fun onLoginChanged(email: String)

    fun onPasswordChanged(password: String)

    fun onSignIn()

    fun onSignUp()
}