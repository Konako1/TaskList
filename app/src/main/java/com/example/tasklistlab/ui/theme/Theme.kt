package com.example.tasklistlab.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Cyan400,
    primaryVariant = Cyan950,
    onPrimary = Black,
    secondary = Blue400,
    secondaryVariant = Blue400,
    onSecondary = Black
)

private val LightColorPalette = lightColors(
    primary = Cyan600,
    primaryVariant = Cyan950,
    onPrimary = White,
    secondary = Blue400,
    secondaryVariant = Blue700,
    onSecondary = Black
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
