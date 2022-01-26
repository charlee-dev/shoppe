package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.planner.PlannerComponent
import com.adwi.shoppe.ui.compose.composables.ComingSoonText
import com.adwi.shoppe.ui.compose.composables.PageHeader
import com.adwi.shoppe.ui.compose.composables.PageLayout
import com.adwi.shoppe.ui.compose.resources.Resources
import com.arkivanov.decompose.extensions.compose.jetbrains.Children

@Composable
fun PlannerContent(
    component: PlannerComponent,
) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is PlannerComponent.Child.Data1 -> PlannerBody(component = child.component)
            }
        }
    }
}

@Composable
fun PlannerBody(
    modifier: Modifier = Modifier,
    component: PlannerComponent,
) {
    PageLayout(
        modifier = modifier,
        title = {
            PageHeader(
                text = "Planner",
                modifier = Modifier.padding(start = Resources.dimens.paddingValues)
            )
        },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ComingSoonText(
                text = "Planner",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}