package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.adwi.shoppe.ui.compose.composables.ShoppeSpacer

@ExperimentalMaterialApi
@Composable
fun ShopPreviewContent(
    component: ShopPreviewComponent,
    shopId: String?,
) {
    ShopPreviewBody(component = component, shopId = shopId)
}

@Composable
fun ShopPreviewBody(
    modifier: Modifier = Modifier,
    component: ShopPreviewComponent,
    shopId: String?,
) {
    val model by component.model.collectAsState(ShopPreviewComponent.Model())

    Box(modifier = modifier.fillMaxSize()) {
        Text(text = "Preview - shopId = $shopId", modifier = Modifier.align(Alignment.Center))
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = model.shop.name)
            ShoppeSpacer()
            Text(text = model.shop.description)
        }
    }
}