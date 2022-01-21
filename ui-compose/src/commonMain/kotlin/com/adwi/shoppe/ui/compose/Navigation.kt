package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.adwi.shoppe.feature.navigation.NavigationComponent
import com.adwi.shoppe.feature.navigation.NavigationComponent.Child
import com.adwi.shoppe.ui.compose.resources.Resources
import com.arkivanov.decompose.extensions.compose.jetbrains.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationContent(
    component: NavigationComponent,
    topInset: Dp,
    bottomInset: Dp,
) {
    Box {
        Scaffold(
            modifier = Modifier.padding(bottom = Resources.dimens.barHeight + bottomInset),
            content = {
                LibraryBody(
                    component = component,
                    topInset = topInset
                )
            },
            scaffoldState = rememberScaffoldState(),
        )

        LibraryBottomBar(
            component = component,
            bottomInset = bottomInset,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun LibraryBody(
    component: NavigationComponent,
    topInset: Dp,
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

@Composable
private fun LibraryBottomBar(
    component: NavigationComponent,
    bottomInset: Dp,
    modifier: Modifier = Modifier,
) {
    val activeIndex by component.activeChildIndex.subscribeAsState()

    BottomNavigation(
        modifier = modifier.height(Resources.dimens.barHeight + bottomInset)
    ) {
        Resources.arrays.shelves.forEachIndexed { index, shelfTitle ->
            BottomNavigationItem(
                onClick = { component.onChildSelect(index) },
                selected = index == activeIndex,
                modifier = Modifier.padding(bottom = bottomInset),
                label = { Text(shelfTitle) },
                icon = {
                    Icon(
                        contentDescription = shelfTitle,
                        imageVector = Resources.arrays.shelfIcons[index]
                    )
                }
            )
        }
    }
}

