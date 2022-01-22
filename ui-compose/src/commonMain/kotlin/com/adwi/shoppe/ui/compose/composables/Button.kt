package com.adwi.shoppe.ui.compose.composables

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShoppeButton(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    shape: Shape = RoundedCornerShape(percent = 20),
    elevation: Dp = 10.dp,
    buttonColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    isLoading: Boolean = false,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressed = updateTransition(label = "Card", targetState = isPressed)
    val scaleState by pressed.animateFloat(label = "Scale") { if (it) .99f else 1f }
    val elevationState by pressed.animateDp(label = "Elevation") { if (it) elevation / 2 else elevation }

    Surface(
        elevation = elevationState,
        shape = shape,
        modifier = modifier
            .width(200.dp)
            .height(70.dp)
            .graphicsLayer {
                scaleX = scaleState
                scaleY = scaleState
            },
    ) {
        Button(
            onClick = onClick,
            shape = shape,
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor,
                contentColor = contentColor
            ),
            modifier = Modifier.fillMaxSize(),
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = contentColor)
            } else {
                label()
            }
        }
    }
}