package com.andrev133.photoalbummapapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionMarkerEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoEntity

data class CollectionWithPhotosRelation(
    @Embedded
    val collection: PhotoCollectionMarkerEntity,
    @Relation(
        parentColumn = "colorCode",
        entityColumn = "markerColorCode"
    )
    val photos: List<PhotoEntity>
)

