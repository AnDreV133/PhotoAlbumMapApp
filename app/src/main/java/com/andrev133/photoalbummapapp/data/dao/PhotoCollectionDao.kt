package com.andrev133.photoalbummapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.relation.CollectionWithLabel
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoCollectionDao {
    @Insert
    suspend fun insertCollection(collection: PhotoCollectionEntity): Long

    @Update
    suspend fun updateCollection(collection: PhotoCollectionEntity)

    @Delete
    suspend fun deleteCollection(collection: PhotoCollectionEntity)

    @Query("SELECT * FROM photo_collections")
    fun getAllCollections(): Flow<List<PhotoCollectionEntity>>

    @Query("SELECT * FROM photo_collections WHERE id = :id")
    suspend fun getCollectionById(id: Long): PhotoCollectionEntity?

    @Query("SELECT * FROM photo_collections WHERE labelColorCode = :labelColorCode")
    fun getCollectionsByLabel(labelColorCode: String): Flow<List<PhotoCollectionEntity>>

    @Query("SELECT * FROM photo_collections WHERE mapPoint = :mapPoint")
    fun getCollectionsByLocation(mapPoint: String): Flow<List<PhotoCollectionEntity>>

    // Получение коллекций с информацией о лейбле
    @Query("""
        SELECT pc.*, l.name as labelName 
        FROM photo_collections pc
        INNER JOIN labels l ON pc.labelColorCode = l.colorCode
    """)
    fun getCollectionsWithLabels(): Flow<List<CollectionWithLabel>>
}

