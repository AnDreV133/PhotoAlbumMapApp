package com.andrev133.photoalbummapapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.andrev133.photoalbummapapp.data.entity.MarkerEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.relation.CollectionWithMarkerRelation
import com.andrev133.photoalbummapapp.data.relation.FullCollectionRelation
import com.andrev133.photoalbummapapp.domain.util.getColorWithFullAlpha
import com.andrev133.photoalbummapapp.domain.util.getPointFromJsonString
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import ru.sulgik.mapkit.geometry.Point

class PhotoCollectionMarkerModel(
    val color: Color,
    val title: String,
    val time: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault()),
    val point: Point = Point(0.0, 0.0),
    val photos: List<PhotoModel> = emptyList()
)

fun FullCollectionRelation.toModel() = PhotoCollectionMarkerModel(
    color = getColorWithFullAlpha(marker.colorCode),
    title = marker.title,
    time = LocalDate.parse(collection.time),
    point = getPointFromJsonString(collection.mapPoint),
    photos = photos.map { PhotoModel(path = it.path) }
)

fun CollectionWithMarkerRelation.toModel() = PhotoCollectionMarkerModel(
    color = getColorWithFullAlpha(marker.colorCode),
    title = marker.title,
    time = LocalDate.parse(collection.time),
    point = getPointFromJsonString(collection.mapPoint),
)

fun MarkerEntity.toModel() = PhotoCollectionMarkerModel(
    color = getColorWithFullAlpha(colorCode),
    title = title
)

fun PhotoCollectionMarkerModel.toPhotoCollectionEntity() = PhotoCollectionEntity(
    markerColorCode = color.toArgb(),
    time = time.toString(),
    mapPoint = "{latitude: ${point.latitude.value}, longitude: ${point.longitude.value}}",
)

fun PhotoCollectionMarkerModel.toMarkerEntity() = MarkerEntity(
    title = title,
    colorCode = color.toArgb(),
)