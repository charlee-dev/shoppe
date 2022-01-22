package com.adwi.shoppe.ui.compose.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSize { Compact, Medium, Expanded }

fun getWindowSizeClass(windowWidth: Dp): WindowSize = when {
    windowWidth < 0.dp -> throw IllegalArgumentException("Dp value cannot be negative")
    windowWidth < 600.dp -> WindowSize.Compact
    windowWidth < 840.dp -> WindowSize.Medium
    else -> WindowSize.Expanded
}