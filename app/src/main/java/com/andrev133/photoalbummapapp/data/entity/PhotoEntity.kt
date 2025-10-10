package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "photos",
    foreignKeys = [
        ForeignKey(
            entity = PhotoCollectionEntity::class,
            parentColumns = ["id"],
            childColumns = ["photoCollectionId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["photoCollectionId"])]
)
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val photoCollectionId: Long,
    val path: String
)