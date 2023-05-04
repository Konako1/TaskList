package com.example.tasklistlab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Blue400,
    primaryVariant = Blue950,
    onPrimary = Black,
    secondary = Violet400,
    secondaryVariant = Violet400,
    onSecondary = White,
    background = DarkGray,
    onBackground = Black,
)

private val LightColorPalette = lightColors(
    primary = Blue600,
    primaryVariant = Blue950,
    onPrimary = White,
    secondary = Violet400,
    secondaryVariant = Violet700,
    onSecondary = Black,
    background = NotReallyWhite,
    onBackground = Black
)

@Composable
fun TaskListTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
