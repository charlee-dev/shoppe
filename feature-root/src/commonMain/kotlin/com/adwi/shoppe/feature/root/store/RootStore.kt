package com.adwi.shoppe.feature.root.store

import com.arkivanov.mvikotlin.core.store.Store

internal interface RootStore : Store<RootStore.Intent, Unit, Unit> {

    sealed class Intent {
        object CheckUserState : Intent()
    }
}