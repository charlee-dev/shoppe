package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoadingStatusWithMessage(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isEmpty: Boolean,
    message: String = "No shops",
    content: @Composable () -> Unit,
) {
    if (isEmpty) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            } else {
                Text(
                    text = message,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.Center)
                )
                ShoppeSpacer()
                content()
            }
        }
    }
}