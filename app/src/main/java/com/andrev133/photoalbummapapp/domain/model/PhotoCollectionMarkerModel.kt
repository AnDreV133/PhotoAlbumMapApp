package com.andrev133.photoalbummapapp.domain.model

import com.andrev133.photoalbummapapp.data.relation.CollectionWithMarkerRelation
import com.andrev133.photoalbummapapp.data.relation.FullCollectionRelation
import com.andrev133.photoalbummapapp.domain.util.getPointFromJsonString
import kotlinx.datetime.LocalDate
import ru.sulgik.mapkit.geometry.Point

class PhotoCollectionMarkerModel(
    val colorCode: Int,
    val title: String,
    val time: LocalDate,
    val point: Point,
    val photos: List<PhotoModel>
)

fun FullCollectionRelation.toModel() = PhotoCollectionMarkerModel(
    colorCode = marker.colorCode,
    title = marker.title,
    time = LocalDate.parse(collection.time),
    point = getPointFromJsonString(collection.mapPoint),
    photos = photos.map { PhotoModel(path = it.path) }
)

fun CollectionWithMarkerRelation.toModel() = PhotoCollectionMarkerModel(
    colorCode = marker.colorCode,
    title = marker.title,
    time = LocalDate.parse(collection.time),
    point = getPointFromJsonString(collection.mapPoint),
    photos = emptyList()
)
