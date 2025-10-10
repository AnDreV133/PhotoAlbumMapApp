package com.andrev133.photoalbummapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andrev133.photoalbummapapp.data.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {
    @Insert
    suspend fun insertPhotoPath(photoPath: PhotoEntity): Long

    @Insert
    suspend fun insertPhotoPaths(photoPaths: List<PhotoEntity>)

    @Update
    suspend fun updatePhotoPath(photoPath: PhotoEntity)

    @Delete
    suspend fun deletePhotoPath(photoPath: PhotoEntity)

    @Query("SELECT * FROM photo_paths WHERE markerColorCode = :markerColorCode")
    fun getPhotoPathsByCollection(markerColorCode: Long): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photo_paths WHERE id = :id")
    suspend fun getPhotoPathById(id: Long): PhotoEntity?

    @Query("DELETE FROM photo_paths WHERE markerColorCode = :markerColorCode")
    suspend fun deletePhotoPathsByCollection(markerColorCode: Long)
}