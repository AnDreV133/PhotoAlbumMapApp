package com.andrev133.photoalbummapapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andrev133.photoalbummapapp.data.entity.LabelEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoPathEntity

data class FullCollectionInfo(
    @Embedded
    val collection: PhotoCollectionEntity,
    @Relation(
        parentColumn = "labelColorCode",
        entityColumn = "colorCode"
    )
    val label: LabelEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "collectionId"
    )
    val photoPaths: List<PhotoPathEntity>
)