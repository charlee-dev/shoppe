package com.adwi.shoppe.feature.navigation

import com.adwi.shoppe.feature.dashboard.DashboardComponent
import com.adwi.shoppe.feature.manager.ManagerComponent
import com.adwi.shoppe.feature.planner.PlannerComponent
import com.adwi.shoppe.feature.settings.SettingsComponent
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value

interface NavigationComponent {

    sealed class Child(val index: Int) {
        data class Dashboard(val component: DashboardComponent) : Child(0)
        data class Manager(val component: ManagerComponent) : Child(1)
        data class Planner(val component: PlannerComponent) : Child(2)
        data class Settings(val component: SettingsComponent) : Child(3)
    }

    val routerState: Value<RouterState<*, Child>>

    val activeChildIndex: Value<Int>

    fun onChildSelect(index: Int)
}