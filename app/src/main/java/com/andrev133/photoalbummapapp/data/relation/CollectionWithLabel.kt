package com.andrev133.photoalbummapapp.data.relation

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity

data class CollectionWithLabel(
    @Embedded
    val collection: PhotoCollectionEntity,
    @ColumnInfo(name = "labelName")
    val labelName: String
)
