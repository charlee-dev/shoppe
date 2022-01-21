package com.adwi.shoppe.feature.root.store

import com.arkivanov.mvikotlin.core.store.Store

internal interface RootStore : Store<RootStore.Intent, Unit, Unit> {

    sealed class Intent {
        data class SignIn(val email: String, val password: String) : Intent()
        data class SignUp(val email: String, val password: String) : Intent()
    }
}