package com.andrev133.photoalbummapapp.data.dao

import androidx.room.*
import com.andrev133.photoalbummapapp.data.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(label: MarkerEntity)

    @Update
    suspend fun updateMarker(label: MarkerEntity)

    @Delete
    suspend fun deleteMarker(label: MarkerEntity)

    @Query("SELECT * FROM markers")
    fun getAllMarkers(): Flow<List<MarkerEntity>>

    @Query("SELECT * FROM markers WHERE colorCode = :colorCode")
    suspend fun getMarkerByColorCode(colorCode: String): MarkerEntity?

    @Query("SELECT * FROM markers WHERE title LIKE :title")
    fun findMarkerByName(title: String): Flow<List<MarkerEntity>>
}

