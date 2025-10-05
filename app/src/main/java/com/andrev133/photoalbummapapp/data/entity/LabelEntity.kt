package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labels")
data class LabelEntity(
    @PrimaryKey
    val colorCode: Int,
    val name: String
)


