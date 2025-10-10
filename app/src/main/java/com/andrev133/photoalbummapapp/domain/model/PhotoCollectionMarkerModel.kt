package com.andrev133.photoalbummapapp.domain.model

import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionMarkerEntity
import com.andrev133.photoalbummapapp.data.relation.CollectionWithPhotosRelation
import com.andrev133.photoalbummapapp.domain.model.serialise.PointSerialized
import com.andrev133.photoalbummapapp.domain.model.serialise.toMapKitPoint
import com.andrev133.photoalbummapapp.domain.util.getPointFromJsonString
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import ru.sulgik.mapkit.geometry.Point

class PhotoCollectionMarkerModel(
    val colorCode: Int,
    val title: String,
    val time: LocalDate,
    val point: Point,
    val photos: List<PhotoModel>
)

fun PhotoCollectionMarkerEntity.toMarker(): PhotoCollectionMarkerModel {
    return PhotoCollectionMarkerModel(
        colorCode = colorCode,
        title = title,
        time = LocalDate.parse(time),
        point = getPointFromJsonString(mapPoint),
        photos = emptyList()
    )
}

fun CollectionWithPhotosRelation.toMarker(): PhotoCollectionMarkerModel {
    return PhotoCollectionMarkerModel(
        colorCode = collection.colorCode,
        title = collection.title,
        time = LocalDate.parse(collection.time),
        point = getPointFromJsonString(collection.mapPoint),
        photos = photos.map { PhotoModel(path = it.path) }
    )
}
