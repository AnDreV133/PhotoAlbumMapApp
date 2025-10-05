package com.andrev133.photoalbummapapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoPathEntity

data class CollectionWithPhotos(
    @Embedded
    val collection: PhotoCollectionEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "collectionId"
    )
    val photoPaths: List<PhotoPathEntity>
)

