package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adwi.shoppe.feature.dashboard.DashboardComponent

@Composable
fun DashboardContent(
    component: DashboardComponent,
) {
    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Dashboard", modifier = Modifier.align(Alignment.Center))
                Button(
                    onClick = { component.singOut() }
                ) {
                    Text(text = "Sign out")
                }
            }
        }
    )
}