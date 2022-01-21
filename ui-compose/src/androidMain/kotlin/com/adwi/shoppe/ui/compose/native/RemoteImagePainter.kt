package com.adwi.shoppe.ui.compose.native

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberImagePainter

@Composable
actual fun remoteImagePainter(
    url: String,
): Painter = rememberImagePainter(url)