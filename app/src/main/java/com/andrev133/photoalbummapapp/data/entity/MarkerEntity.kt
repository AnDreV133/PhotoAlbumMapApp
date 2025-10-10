package com.andrev133.photoalbummapapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerEntity(
    @PrimaryKey
    val colorCode: Int,
    val title: String
)


