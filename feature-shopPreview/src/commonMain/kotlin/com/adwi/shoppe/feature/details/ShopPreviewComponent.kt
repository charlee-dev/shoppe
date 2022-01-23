package com.adwi.shoppe.feature.details

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface ShopPreviewComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Preview(val name: String) : Child()
        data class Data2(val name: String) : Child()
    }

    fun onData()
}