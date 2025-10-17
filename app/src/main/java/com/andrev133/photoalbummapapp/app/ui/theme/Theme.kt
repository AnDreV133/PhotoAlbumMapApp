package com.andrev133.photoalbummapapp.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

private val DarkColorScheme = lightColorScheme(
    background = Color.Black,
    surface = Color(0xFF1F291E),
    surfaceVariant = Color(0xFF333B33),
    primary = Color(0xFF334434),
    onPrimary = Color(0xFFADFA96),
    tertiary = Color(0xFF199223),
    onTertiary = Color(0xFFADFA96)
)

private val LightColorScheme = DarkColorScheme

val Shapes.startRoundedRect: RoundedCornerShape
    get() = RoundedCornerShape(
        topStart = 16.dp,
        bottomStart = 16.dp,
        topEnd = 0.dp,
        bottomEnd = 0.dp
    )

val Shapes.endRoundedRect: RoundedCornerShape
    get() = RoundedCornerShape(
        topStart = 0.dp,
        bottomStart = 0.dp,
        topEnd = 16.dp,
        bottomEnd = 16.dp
    )

@Composable
fun AutoMediaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes(),
        content = content
    )
}