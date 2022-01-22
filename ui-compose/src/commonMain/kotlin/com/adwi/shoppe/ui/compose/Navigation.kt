package com.adwi.shoppe.ui.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.navigation.NavigationComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent.Child
import com.adwi.shoppe.ui.compose.composables.ShoppeBottomNav
import com.adwi.shoppe.ui.compose.composables.SideMenu
import com.adwi.shoppe.ui.compose.resources.HomeSections
import com.adwi.shoppe.ui.compose.resources.Resources
import com.adwi.shoppe.ui.compose.util.WindowSize
import com.adwi.shoppe.ui.compose.util.getWindowSizeClass
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun NavigationContent(
    component: NavigationComponent,
    windowWidth: Dp,
    topInset: Dp,
    bottomInset: Dp,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val width by remember { mutableStateOf(windowWidth) }
        val windowSize = getWindowSizeClass(width)

        val navItems = HomeSections.values().toList()
        val activeIndex by component.activeChildIndex.subscribeAsState()

        Box {
            Scaffold(
                modifier = Modifier
                    .padding(
                        bottom = if (windowSize == WindowSize.Expanded) 0.dp else Resources.dimens.barHeight + bottomInset
                    ),
                content = {
                    LibraryBody(
                        component = component,
                        paddingValues = when (windowSize) {
                            WindowSize.Compact -> PaddingValues(top = topInset)
                            WindowSize.Medium -> PaddingValues(top = topInset)
                            WindowSize.Expanded -> PaddingValues(start = Resources.dimens.sideMenuWidth)
                        }
                    )
                },
                scaffoldState = rememberScaffoldState(),
            )
            when (windowSize) {
                WindowSize.Compact -> ShoppeBottomNav(
                    items = navItems,
                    currentIndex = activeIndex,
                    onIndexSelected = { component.onChildSelect(it) },
                    paddingValues = PaddingValues(bottom = bottomInset + 16.dp, start = 32.dp, end = 32.dp),
                )
                WindowSize.Medium -> ShoppeBottomNav(
                    items = navItems,
                    currentIndex = activeIndex,
                    onIndexSelected = { component.onChildSelect(it) },
                    paddingValues = PaddingValues(bottom = bottomInset + 16.dp, start = 32.dp, end = 32.dp),
                )
                WindowSize.Expanded -> SideMenu(
                    items = navItems,
                    currentIndex = activeIndex,
                    onIndexSelected = { component.onChildSelect(it) },
                )
            }

        }
    }
}

@Composable
private fun LibraryBody(
    component: NavigationComponent,
    paddingValues: PaddingValues,
) {
    Box(modifier = Modifier.padding(paddingValues = paddingValues)) {
        Children(component.routerState) {
            it.instance.let { child ->
                when (child) {
                    is Child.Dashboard -> DashboardContent(child.component)
                    is Child.Manager -> ManagerContent(child.component)
                    is Child.Planner -> PlannerContent(child.component)
                    is Child.Settings -> SettingsContent(child.component)
                }
            }
        }
    }
}
