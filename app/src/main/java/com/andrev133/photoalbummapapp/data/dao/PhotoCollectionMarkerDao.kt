package com.andrev133.photoalbummapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionMarkerEntity
import com.andrev133.photoalbummapapp.data.relation.CollectionWithPhotosRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoCollectionMarkerDao {
    @Insert
    suspend fun insertCollection(collection: PhotoCollectionMarkerEntity): Long

    @Update
    suspend fun updateCollection(collection: PhotoCollectionMarkerEntity)

    @Delete
    suspend fun deleteCollection(collection: PhotoCollectionMarkerEntity)

    @Query("SELECT * FROM photo_collections")
    fun getAllCollections(): Flow<List<PhotoCollectionMarkerEntity>>

    @Transaction
    @Query("SELECT * FROM photo_collections WHERE colorCode = :colorCode")
    suspend fun getCollectionByColorCode(colorCode: Int): CollectionWithPhotosRelation?

    @Query("SELECT * FROM photo_collections WHERE colorCode = :labelColorCode")
    fun getCollectionsByLabel(labelColorCode: String): Flow<List<PhotoCollectionMarkerEntity>>

    @Transaction
    @Query("SELECT * FROM photo_collections WHERE mapPoint = :mapPoint")
    fun getCollectionsByLocation(mapPoint: String): CollectionWithPhotosRelation?
}

