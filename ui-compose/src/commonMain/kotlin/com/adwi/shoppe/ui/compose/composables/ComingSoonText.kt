package com.adwi.shoppe.ui.compose.composables

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ComingSoonText(
    modifier: Modifier = Modifier,
    text: String = "",
    shape: Shape = RoundedCornerShape(percent = 35),
    elevation: Dp = 20.dp,
    backgroundColor: Color = MaterialTheme.colors.surface,
    gradientColor: Color = MaterialTheme.colors.primaryVariant,
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    val elevationState by animateDpAsState(
        targetValue = if (state.targetState) elevation else 0.dp,
        animationSpec = tween(1000)
    )
    val horizontalGradientBrush = Brush.linearGradient(
        colors = listOf(gradientColor, backgroundColor)
    )

    Card(
        elevation = elevationState,
        shape = shape,
        backgroundColor = Color.Transparent,
        modifier = modifier
            .padding(32.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(shape)
                .background(horizontalGradientBrush)
        ) {
            Text(
                text = "$text coming here soon..",
                style = MaterialTheme.typography.h2.copy(color = MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .padding(32.dp)
                    .alpha(.7f),
            )
        }
    }
}