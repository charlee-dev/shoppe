package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.adwi.shoppe.feature.manager.ManagerComponent
import com.adwi.shoppe.ui.compose.composables.ComingSoonText

@Composable
fun ManagerContent(
    component: ManagerComponent,
) {
    Scaffold(
        backgroundColor = Color.Transparent,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            ComingSoonText(
                text = "Manager",
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}