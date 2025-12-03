package com.andrev133.photoalbummapapp.app.compose.util

import android.net.Uri
import androidx.annotation.FloatRange
import androidx.compose.ui.graphics.Color
import ru.sulgik.mapkit.geometry.Point
import kotlin.math.pow
import kotlin.math.sqrt

fun Color.darken(
    @FloatRange(from = 0.0, to = 1.0) darkenBy: Float = 0.3f
): Color {
    return copy(
        red = red * (1f - darkenBy),
        green = green * (1f - darkenBy),
        blue = blue * (1f - darkenBy),
        alpha = alpha
    )
}

fun Point.distanceTo(point: Point): Float {
    return sqrt(
        (latitude.value - point.latitude.value).pow(2.0)
                + (longitude.value - point.longitude.value).pow(2.0)
    ).toFloat()
}

// Сериализация: URI -> String
fun Uri.serialize(): String = this.toString()

// Десериализация: String -> URI
fun String.toUri(): Uri? = Uri.parse(this)