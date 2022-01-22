package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun BottomText(
    modifier: Modifier = Modifier,
    layoutId: String = "",
    message: String = "Don't have an account?",
    onClick: () -> Unit,
    buttonText: String = "Sign up",
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = message,
            modifier = Modifier.alpha(.7f)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Surface(onClick = onClick, color = Color.Transparent) {
            Text(
                text = buttonText,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
        }
    }
}