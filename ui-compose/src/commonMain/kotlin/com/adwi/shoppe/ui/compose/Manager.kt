package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.manager.ManagerComponent
import com.adwi.shoppe.ui.compose.composables.LoadingStatusWithMessage
import com.adwi.shoppe.ui.compose.composables.PageHeader
import com.adwi.shoppe.ui.compose.composables.PageLayout
import com.adwi.shoppe.ui.compose.composables.ShoppeButton
import com.adwi.shoppe.ui.compose.resources.Resources
import com.arkivanov.decompose.extensions.compose.jetbrains.Children

@ExperimentalMaterialApi
@Composable
fun ManagerContent(
    component: ManagerComponent,
) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is ManagerComponent.Child.Manager -> ManagerBody(component = child.component)
                is ManagerComponent.Child.PreviewShop -> ShopPreviewContent(component = child.component, child.shopId)
            }
        }
    }
}

@Composable
fun ManagerBody(
    component: ManagerComponent,
) {
    val model by component.model.collectAsState(ManagerComponent.Model())
    val shops by model.shopItems.collectAsState(emptyList())

    PageLayout(
        title = {
            PageHeader(
                text = "Manager",
            )
        },
        padding = Resources.dimens.paddingValues
    ) {
        if (shops.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = shops) {
                    Text(text = it.name)
                }
            }
        } else {
            LoadingStatusWithMessage(
                isLoading = model.isRefreshing,
                isEmpty = shops.isEmpty(),
                modifier = Modifier.fillMaxSize()
            ) {
                ShoppeButton(
                    label = { Text(text = "Add shop") },
                    onClick = { component.onAddShopClick() }
                )
            }
        }
    }
}

