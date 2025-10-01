package com.example.ahmad_ghozali.ui.theme


import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF0D47A1),
    onPrimary = Color.White,
    secondary = Color(0xFF0288D1)
)

@Composable
fun MasterDetailTheme(content: @Composable ()->Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        typography = Typography(),
        content = content
    )
}
