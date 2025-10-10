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

    @Query("SELECT * FROM photos WHERE id = :id")
    suspend fun getPhotoById(id: Long): PhotoEntity?

    @Query("SELECT * FROM photos WHERE photoCollectionId = :photoCollectionId")
    suspend fun getPhotoByCollectionId(photoCollectionId: Long): PhotoEntity?
}