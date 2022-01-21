package com.adwi.shoppe.ui.compose.resources

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

object ColorPalette {

    val DarkColorPalette = darkColors(
        primary = Colors.PrimaryDark,
        primaryVariant = Colors.PrimaryVariant,
        onPrimary = Colors.Neutral1,
        secondary = Colors.SecondaryDark,
        secondaryVariant = Colors.SecondaryVariant,
        onSecondary = Colors.Neutral8,
        onBackground = Colors.Neutral1,
    )

    val LightColorPalette = lightColors(
        primary = Colors.Primary,
        primaryVariant = Colors.PrimaryVariant,
        onPrimary = Color.White,
        secondary = Colors.Secondary,
        secondaryVariant = Colors.SecondaryVariant,
        onSecondary = Colors.Neutral8,
        background = Color.White,
        onBackground = Colors.Neutral8,
        surface = Colors.PrimaryLight,
        onSurface = Colors.Neutral6
    )
}