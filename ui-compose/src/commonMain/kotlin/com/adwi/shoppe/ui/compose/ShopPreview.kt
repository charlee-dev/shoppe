package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.ui.compose.composables.PageHeader
import com.adwi.shoppe.ui.compose.composables.PageLayout
import com.adwi.shoppe.ui.compose.composables.ShoppeButton
import com.adwi.shoppe.ui.compose.composables.ShoppeSpacer
import com.adwi.shoppe.ui.compose.composables.ShoppeTextField
import com.adwi.shoppe.ui.compose.resources.Resources
import com.arkivanov.decompose.extensions.compose.jetbrains.Children

@ExperimentalMaterialApi
@Composable
fun ShopPreviewContent(
    component: ShopPreviewComponent,
    shopId: String,
) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is ShopPreviewComponent.Child.Preview -> ShopPreviewBody(component, shopId, ShopPreviewMode.PREVIEW)
                is ShopPreviewComponent.Child.Edit -> ShopPreviewBody(component, shopId, ShopPreviewMode.EDIT)
                is ShopPreviewComponent.Child.Add -> ShopPreviewBody(component, shopId, ShopPreviewMode.ADD)
            }
        }
    }
}

enum class ShopPreviewMode(val title: String) {
    PREVIEW("Shop preview"),
    EDIT("Edit shop"),
    ADD("Add shop")
}

@ExperimentalMaterialApi
@Composable
fun ShopPreviewBody(
    component: ShopPreviewComponent,
    shopId: String,
    mode: ShopPreviewMode,
) {
    val model by component.model.collectAsState(ShopPreviewComponent.Model())

    PageLayout(
        title = {
            PageHeader(
                text = mode.title,
            )
        },
        padding = Resources.dimens.paddingValues
    ) {
        when (mode) {
            ShopPreviewMode.PREVIEW -> ModePreview(component = component)
            ShopPreviewMode.EDIT -> ShopEditFields(
                shop = model.shop,
                onSaveClick = { name, description ->
                    component.saveEdit(shopId, name, description, "") // TODO(add imageUrl impl)
                }
            )
            ShopPreviewMode.ADD -> ShopEditFields(
                shop = model.shop,
                onSaveClick = { name, description ->
                    component.saveAdd(name, description, "") // TODO(add imageUrl impl)
                }
            )
        }
    }
}

@Composable
private fun ModePreview(
    modifier: Modifier = Modifier,
    component: ShopPreviewComponent,
) {
    val model by component.model.collectAsState(ShopPreviewComponent.Model())

    Row {
        Text(text = "Name")
        Text(text = model.shop.name)
    }
    ShoppeSpacer()
    Column {
        Text(text = "Description")
        Text(text = model.shop.name)
    }
}

@ExperimentalMaterialApi
@Composable
private fun ShopEditFields(
    shop: ShopPreviewComponent.ShopItem,
    onSaveClick: (name: String, description: String) -> Unit,
) {
    var name by remember { mutableStateOf(shop.name) }
    var description by remember { mutableStateOf(shop.description) }

    ShoppeTextField(
        text = shop.name,
        onTextChange = { name = it }
    )
    ShoppeSpacer()
    ShoppeTextField(
        text = shop.description,
        onTextChange = { description = it }
    )
    ShoppeSpacer()
    ShoppeButton(
        label = { Text(text = "Save") },
        onClick = { onSaveClick(name, description) }
    )
}