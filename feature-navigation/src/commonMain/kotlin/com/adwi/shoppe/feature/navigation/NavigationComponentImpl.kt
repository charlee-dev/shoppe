package com.adwi.shoppe.feature.navigation

import com.adwi.shoppe.feature.dashboard.DashboardComponent
import com.adwi.shoppe.feature.manager.ManagerComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent.Child
import com.adwi.shoppe.feature.planner.PlannerComponent
import com.adwi.shoppe.feature.settings.SettingsComponent
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.factory

class NavigationComponentImpl(
    override val di: DI,
    private val componentContext: ComponentContext,
) : NavigationComponent, DIAware, ComponentContext by componentContext {

    private val dashboard by factory<ComponentContext, DashboardComponent>()
    private val manager by factory<ComponentContext, ManagerComponent>()
    private val planner by factory<ComponentContext, PlannerComponent>()
    private val settings by factory<ComponentContext, SettingsComponent>()

    private val router = router<Config, Child>(
        initialConfiguration = Config.Dashboard
    ) { configuration, componentContext ->
        when (configuration) {
            is Config.Dashboard -> Child.Dashboard(dashboard(componentContext))
            is Config.Manager -> Child.Manager(manager(componentContext))
            is Config.Planner -> Child.Planner(planner(componentContext))
            is Config.Settings -> Child.Settings(settings(componentContext))
        }
    }

    override val routerState: Value<RouterState<*, Child>> = router.state

    override val activeChildIndex: Value<Int> = routerState.map {
        it.activeChild.instance.index
    }

    override fun onChildSelect(index: Int) {
        router.push(
            when (index) {
                0 -> Config.Dashboard
                1 -> Config.Manager
                2 -> Config.Planner
                else -> Config.Settings
            }
        )
    }

    sealed class Config : Parcelable {
        @Parcelize
        object Dashboard : Config()
        @Parcelize
        object Manager : Config()
        @Parcelize
        object Planner : Config()
        @Parcelize
        object Settings : Config()
    }
}