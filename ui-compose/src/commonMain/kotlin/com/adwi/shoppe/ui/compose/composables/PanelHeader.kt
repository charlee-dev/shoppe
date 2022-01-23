package com.adwi.shoppe.ui.compose.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PanelHeader(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colors.onSurface,
        style = MaterialTheme.typography.body2,
        fontWeight = FontWeight.Bold,
        modifier = modifier.alpha(.5f)
    )
}