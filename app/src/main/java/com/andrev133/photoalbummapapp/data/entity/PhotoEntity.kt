package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "photo_paths",
    foreignKeys = [
        ForeignKey(
            entity = PhotoCollectionMarkerEntity::class,
            parentColumns = ["colorCode"],
            childColumns = ["markerColorCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["markerColorCode"])]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val markerColorCode: Long,
    val path: String
)