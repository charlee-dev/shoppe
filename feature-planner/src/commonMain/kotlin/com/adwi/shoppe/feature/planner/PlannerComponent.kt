package com.adwi.shoppe.feature.planner

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface PlannerComponent {

    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class Data1(val component: PlannerComponent) : Child()
    }

    fun onData()
}