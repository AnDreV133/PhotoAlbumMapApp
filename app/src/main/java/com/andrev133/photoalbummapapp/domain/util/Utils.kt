package com.andrev133.photoalbummapapp.domain.util

import androidx.compose.ui.graphics.Color
import com.andrev133.photoalbummapapp.domain.model.serialise.PointSerialized
import com.andrev133.photoalbummapapp.domain.model.serialise.toMapKitPoint
import kotlinx.serialization.json.Json
import ru.sulgik.mapkit.geometry.Point

fun getPointFromJsonString(str: String): Point = Json
    .decodeFromString<PointSerialized>(str)
    .toMapKitPoint()

fun getColorWithFullAlpha(color: Int): Color {
    return Color(0xFF000000 or color.toLong())
}