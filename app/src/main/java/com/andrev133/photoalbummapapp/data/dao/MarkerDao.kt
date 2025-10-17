package com.andrev133.photoalbummapapp.data.dao

import androidx.room.*
import com.andrev133.photoalbummapapp.data.entity.MarkerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(label: MarkerEntity)

    @Update
    suspend fun update(label: MarkerEntity)

    @Delete
    suspend fun delete(label: MarkerEntity)

    @Upsert
    suspend fun upsert(label: MarkerEntity)

    @Query("DELETE FROM markers WHERE colorCode = :colorCode")
    suspend fun deleteByColorCode(colorCode: Int)

    @Query("SELECT * FROM markers")
    fun getAllMarkers(): Flow<List<MarkerEntity>>

    @Query("SELECT * FROM markers WHERE colorCode = :colorCode")
    suspend fun getMarkerByColorCode(colorCode: Int): MarkerEntity?

    @Query("SELECT * FROM markers WHERE title LIKE :title")
    fun findMarkerByName(title: String): Flow<List<MarkerEntity>>
}

