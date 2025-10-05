package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "photo_collections",
    foreignKeys = [
        ForeignKey(
            entity = LabelEntity::class,
            parentColumns = ["colorCode"],
            childColumns = ["labelColorCode"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("labelColorCode")]
)
data class PhotoCollectionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val labelColorCode: Int,
    val time: String,
    val mapPoint: String
)
