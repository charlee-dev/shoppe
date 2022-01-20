package com.adwi.shoppe.feature.auth.store

import com.adwi.shoppe.feature.auth.store.AuthStore.Intent
import com.adwi.shoppe.feature.auth.store.AuthStore.Label
import com.adwi.shoppe.feature.auth.store.AuthStore.State
import com.arkivanov.mvikotlin.core.store.Store

interface AuthStore : Store<Intent, State, Label> {

    data class State(
        val email: String = "",
        val password: String = "",
        val isLoading: Boolean = false,
    )

    sealed class Intent {
        data class ChangeEmail(val email: String) : Intent()
        data class ChangePassword(val password: String) : Intent()
        object SignIn : Intent()
        object SignUp : Intent()
    }

    sealed class Result {
        data class EmailChanged(val login: String) : Result()
        data class PasswordChanged(val password: String) : Result()
        data class Loading(val isLoading: Boolean) : Result()
    }

    sealed class Label {
        data class MessageReceived(val message: String?) : Label()
    }
}