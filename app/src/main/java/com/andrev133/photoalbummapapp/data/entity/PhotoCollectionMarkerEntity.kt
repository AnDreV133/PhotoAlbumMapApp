package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo_collections",
)
data class PhotoCollectionMarkerEntity(
    @PrimaryKey
    val colorCode: Int,
    val title: String,
    val time: String,
    val mapPoint: String
)
