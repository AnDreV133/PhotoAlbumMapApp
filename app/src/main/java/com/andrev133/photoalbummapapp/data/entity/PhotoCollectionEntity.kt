package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "photo_collections",
    foreignKeys = [
        ForeignKey(
            entity = MarkerEntity::class,
            parentColumns = ["colorCode"],
            childColumns = ["markerColorCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("markerColorCode")]
)
data class PhotoCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val markerColorCode: Int,
    val time: String,
    val mapPoint: String
)
