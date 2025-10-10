package com.andrev133.photoalbummapapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andrev133.photoalbummapapp.data.entity.MarkerEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity

data class CollectionWithMarkerRelation(
    @Embedded
    val collection: PhotoCollectionEntity,
    @Relation(
        parentColumn = "markerColorCode",
        entityColumn = "colorCode"
    )
    val marker: MarkerEntity
)