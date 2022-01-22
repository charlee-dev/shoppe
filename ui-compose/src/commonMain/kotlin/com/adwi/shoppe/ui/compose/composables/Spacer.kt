package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ShoppeSpacer(
    size: Int = 16,
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = modifier.size(size.dp))
}