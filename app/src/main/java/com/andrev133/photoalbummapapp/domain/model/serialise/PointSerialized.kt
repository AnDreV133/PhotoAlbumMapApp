package com.andrev133.photoalbummapapp.domain.model.serialise

import kotlinx.serialization.Serializable
import ru.sulgik.mapkit.geometry.Point

@Serializable
class PointSerialized(
    val latitude: Double,
    val longitude: Double
)

fun PointSerialized.toMapKitPoint() = Point(
    latitude = latitude,
    longitude = longitude
)

fun PointSerialized.fromMapKitPoint(point: Point) = PointSerialized(
    latitude = point.latitude.value,
    longitude = point.longitude.value
)