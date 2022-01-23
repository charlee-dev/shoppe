package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adwi.shoppe.feature.dashboard.DashboardComponent
import com.adwi.shoppe.feature.dashboard.DashboardComponent.Model
import com.adwi.shoppe.ui.compose.composables.DashboardGreeting
import com.adwi.shoppe.ui.compose.composables.DashboardShopsPanel
import com.adwi.shoppe.ui.compose.resources.Resources
import com.arkivanov.decompose.extensions.compose.jetbrains.Children

@ExperimentalMaterialApi
@Composable
fun DashboardContent(
    component: DashboardComponent,
) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is DashboardComponent.Child.Dashboard -> DashboardBody(component = child.component)
                is DashboardComponent.Child.ShopDetails -> ShopPreviewContent(component = child.component)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DashboardBody(
    modifier: Modifier = Modifier,
    component: DashboardComponent,
) {
    val model by component.model.collectAsState(Model())
    val shops by model.shopItems.collectAsState(emptyList())

    Scaffold(
        backgroundColor = Color.Transparent,
    ) {
        Column(modifier = modifier.fillMaxSize()) {
            DashboardGreeting(
                name = "Adrian",
                modifier = Modifier
                    .padding(
                        top = Resources.dimens.barHeight,
                        start = Resources.dimens.paddingValues
                    )
            )
            Button(
                onClick = { component.signOut() },
                modifier = Modifier
                    .padding(
                        top = Resources.dimens.paddingValues,
                        start = Resources.dimens.paddingValues,
                        bottom = Resources.dimens.paddingValues
                    )
            ) {
                Text(text = "Sign out")
            }
            DashboardShopsPanel(
                items = shops,
                onShopClick = { component.onShopClick(it) }
            )
        }
    }
}
