package com.adwi.shoppe.ui.compose

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
        val currentIndex by component.activeChildIndex.subscribeAsState()

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = if (windowSize == WindowSize.Expanded) 0.dp else bottomInset
                ),
            scaffoldState = rememberScaffoldState(),
        ) {
            WindowSizedContainer(
                windowSize = windowSize,
                navItems = navItems,
                currentIndex = currentIndex,
                onIndexSelected = { component.onChildSelect(it) }
            ) {
                LibraryBody(
                    component = component,
                    paddingValues = when (windowSize) {
                        WindowSize.Compact -> PaddingValues(top = topInset)
                        WindowSize.Medium -> PaddingValues(top = topInset)
                        WindowSize.Expanded -> PaddingValues(start = Resources.dimens.sideMenuWidth)
                    }
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun WindowSizedContainer(
    modifier: Modifier = Modifier,
    windowSize: WindowSize,
    navItems: List<HomeSections>,
    currentIndex: Int,
    onIndexSelected: (Int) -> Unit,
    content: @Composable () -> Unit,
) {
    when (windowSize) {
        WindowSize.Compact -> Box(modifier = Modifier
            .fillMaxSize()
        ) {
            ShoppeBottomNav(
                items = navItems,
                currentIndex = currentIndex,
                onIndexSelected = onIndexSelected,
                paddingValues = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            content()
        }
        WindowSize.Medium -> Box(modifier = Modifier
            .fillMaxSize()
        ) {
            ShoppeBottomNav(
                items = navItems,
                currentIndex = currentIndex,
                onIndexSelected = onIndexSelected,
                paddingValues = PaddingValues(start = 16.dp, end = 16.dp),
                modifier = Modifier.align(Alignment.BottomCenter)
            )
            content()
        }
        WindowSize.Expanded -> Row(
            modifier = Modifier.fillMaxSize()
        ) {
            SideMenu(
                items = navItems,
                currentIndex = currentIndex,
                onIndexSelected = onIndexSelected,
                visible = windowSize == WindowSize.Expanded,
                modifier = Modifier
            )
            Surface(
                shape = RoundedCornerShape(2),
                elevation = 0.dp,
                modifier = modifier
                    .padding(vertical = 12.dp)
                    .padding(end = 12.dp)
            ) {
                content()
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun LibraryBody(
    component: NavigationComponent,
    paddingValues: PaddingValues,
) {
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
