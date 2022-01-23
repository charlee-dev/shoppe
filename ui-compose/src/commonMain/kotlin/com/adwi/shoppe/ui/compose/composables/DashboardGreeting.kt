package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun DashboardGreeting(
    modifier: Modifier = Modifier,
    name: String,
    message: String = "Have a nice day at work",
    color: Color = MaterialTheme.colors.onBackground,
    alpha: Float = .8f,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
    ) {
        Text(
            text = "Hello $name",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            letterSpacing = -(2.sp),
            lineHeight = 20.sp,
            modifier = Modifier
                .alpha(.8f)
        )
        Text(
            text = message,
            color = color,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Light,
            letterSpacing = -(1.sp),
            lineHeight = 20.sp,
            modifier = Modifier.alpha(alpha)
        )
    }
}