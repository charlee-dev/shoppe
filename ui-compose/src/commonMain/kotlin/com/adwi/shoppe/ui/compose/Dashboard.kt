package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.dashboard.DashboardComponent
import com.adwi.shoppe.feature.shops.ShopsComponent.Model
import com.adwi.shoppe.feature.upcomingorders.UpcomingOrdersComponent.OrdersModel
import com.adwi.shoppe.ui.compose.composables.DashboardGreeting
import com.adwi.shoppe.ui.compose.composables.DashboardShopsPanel
import com.adwi.shoppe.ui.compose.composables.DashboardUpcomingOrderPanel
import com.adwi.shoppe.ui.compose.composables.PageLayout
import com.adwi.shoppe.ui.compose.composables.ShoppeSpacer
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
                is DashboardComponent.Child.ShopDetails -> ShopPreviewContent(component = child.component, child.shopId)
                is DashboardComponent.Child.OrderDetails -> ShopPreviewContent(component = child.component,
                    child.orderId)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DashboardBody(
    component: DashboardComponent,
) {
    val shopsModel by component.shops.model.collectAsState(Model())
    val shops by shopsModel.shopItems.collectAsState(emptyList())

    val upcomingOrdersModel by component.upcomingOrders.model.collectAsState(OrdersModel())
    val upcomingOrders = upcomingOrdersModel.orderItems

    PageLayout(
        title = {
            DashboardGreeting(
                name = "Adrian",
                modifier = Modifier
                    .padding(start = Resources.dimens.paddingValues)
            )
        },
    ) {
        Button(
            onClick = { component.signOut() },
            modifier = Modifier
                .padding(
                    start = Resources.dimens.paddingValues,
                    bottom = Resources.dimens.paddingValues
                )
        ) {
            Text(text = "Sign out")
        }
        DashboardShopsPanel(
            items = shops,
            onShopClick = { component.shops.onShopClick(it) },
            isLoading = shopsModel.isRefreshing
        )
        ShoppeSpacer(32)
        DashboardUpcomingOrderPanel(
            items = upcomingOrders,
            onOrderClick = { component.shops.onShopClick(it) },
            isLoading = upcomingOrdersModel.isRefreshing
        )
    }
}
