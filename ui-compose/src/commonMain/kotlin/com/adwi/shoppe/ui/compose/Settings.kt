package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.settings.SettingsComponent

@Composable
fun SettingsContent(
    component: SettingsComponent,
) {
    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Settings", modifier = Modifier.align(Alignment.Center))
            }
        }
    )
}