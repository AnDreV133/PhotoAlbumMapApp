package com.andrev133.photoalbummapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andrev133.photoalbummapapp.data.entity.PhotoPathEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoPathDao {
    @Insert
    suspend fun insertPhotoPath(photoPath: PhotoPathEntity): Long

    @Insert
    suspend fun insertPhotoPaths(photoPaths: List<PhotoPathEntity>)

    @Update
    suspend fun updatePhotoPath(photoPath: PhotoPathEntity)

    @Delete
    suspend fun deletePhotoPath(photoPath: PhotoPathEntity)

    @Query("SELECT * FROM photo_paths WHERE collectionId = :collectionId")
    fun getPhotoPathsByCollection(collectionId: Long): Flow<List<PhotoPathEntity>>

    @Query("SELECT * FROM photo_paths WHERE id = :id")
    suspend fun getPhotoPathById(id: Long): PhotoPathEntity?

    @Query("DELETE FROM photo_paths WHERE collectionId = :collectionId")
    suspend fun deletePhotoPathsByCollection(collectionId: Long)
}