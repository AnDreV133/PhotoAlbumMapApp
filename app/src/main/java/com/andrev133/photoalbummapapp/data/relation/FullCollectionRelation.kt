package com.andrev133.photoalbummapapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andrev133.photoalbummapapp.data.entity.MarkerEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoEntity

data class FullCollectionRelation(
    @Embedded
    val collection: PhotoCollectionEntity,
    @Relation(
        parentColumn = "markerColorCode",
        entityColumn = "colorCode"
    )
    val marker: MarkerEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "photoCollectionId"
    )
    val photos: List<PhotoEntity>
)