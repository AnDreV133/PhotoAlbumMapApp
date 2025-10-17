package com.andrev133.photoalbummapapp.app.compose.util

import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color

inline fun Color.darken(
    @FloatRange(from = 0.0, to = 1.0) darkenBy: Float = 0.3f
): Color {
    return copy(
        red = red * (1f - darkenBy),
        green = green * (1f - darkenBy),
        blue = blue * (1f - darkenBy),
        alpha = alpha
    )
}