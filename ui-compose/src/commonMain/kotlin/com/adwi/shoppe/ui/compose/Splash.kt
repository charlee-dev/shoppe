package com.adwi.shoppe.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ShoppingBasket
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.splash.SplashComponent

@Composable
fun SplashContent(
    component: SplashComponent,
) {
    Scaffold(
        content = {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(
                        imageVector = Icons.TwoTone.ShoppingBasket,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primary,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(
                        text = "Shoppe",
                        style = MaterialTheme.typography.h2.copy(color = MaterialTheme.colors.onSurface)
                    )
                }
            }
        }
    )
}