package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.details.ShopPreviewComponent
import com.arkivanov.decompose.extensions.compose.jetbrains.Children

@ExperimentalMaterialApi
@Composable
fun ShopPreviewContent(
    component: ShopPreviewComponent,
) {
    Children(component.routerState) {
        it.instance.let { child ->
            when (child) {
                is ShopPreviewComponent.Child.Preview -> ShopPreviewBody(component = component)
                is ShopPreviewComponent.Child.Data2 -> ShopPreviewBody(component = component) // TODO("add another action")
            }
        }
    }
}

@Composable
fun ShopPreviewBody(
    modifier: Modifier = Modifier,
    component: ShopPreviewComponent,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Preview", modifier = Modifier.align(Alignment.Center))
    }
}