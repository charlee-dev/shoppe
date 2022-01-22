package com.adwi.shoppe.ui.compose.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.shoppe.feature.auth.AuthComponent
import com.adwi.shoppe.ui.compose.resources.Resources
import kotlinx.coroutines.flow.collect

@Composable
fun ShoppeSnackBar(
    snackBarData: SnackbarData,
    modifier: Modifier = Modifier,
    actionOnNewLine: Boolean = false,
    shape: Shape = RoundedCornerShape(percent = 35),
    backgroundColor: Color = MaterialTheme.colors.primary,
    contentColor: Color = MaterialTheme.colors.onPrimary,
    actionColor: Color = MaterialTheme.colors.secondaryVariant,
    elevation: Dp = 20.dp,
) {
    Snackbar(
        snackbarData = snackBarData,
        modifier = modifier,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        actionColor = actionColor,
        elevation = elevation
    )
}

@Composable
fun ShoppeSnackBarHost(
    modifier: Modifier = Modifier,
    component: AuthComponent,
    hostState: SnackbarHostState = SnackbarHostState(),
) {
    SnackbarHost(
        hostState = hostState,
        modifier = modifier
            .wrapContentWidth(align = Alignment.Start)
            .widthIn(max = 550.dp)
            .padding(horizontal = 16.dp)
            .padding(bottom = 64.dp + Resources.dimens.barHeight),
        snackbar = { snackbarData -> ShoppeSnackBar(snackbarData) }
    )

    LaunchedEffect("showError") {
        component.events.collect { event ->
            when (event) {
                is AuthComponent.Event.MessageReceived -> {
                    hostState.showSnackbar(event.message ?: "")
                }
            }
        }
    }
}