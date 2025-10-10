package com.andrev133.photoalbummapapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.relation.CollectionWithMarkerRelation
import com.andrev133.photoalbummapapp.data.relation.CollectionWithPhotosRelation
import com.andrev133.photoalbummapapp.data.relation.FullCollectionRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoCollectionMarkerDao {
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

    @Query("SELECT * FROM photo_collections WHERE markerColorCode = :labelColorCode")
    fun getCollectionsByMarker(labelColorCode: String): Flow<List<PhotoCollectionEntity>>

    @Query("SELECT * FROM photo_collections WHERE mapPoint = :mapPoint")
    fun getCollectionsByLocation(mapPoint: String): Flow<List<PhotoCollectionEntity>>

    @Transaction
    @Query(
        """
        SELECT * FROM markers
            INNER JOIN photo_collections ON markerColorCode = colorCode
        """
    )
    fun getAllCollectionWithLabel(): Flow<List<CollectionWithMarkerRelation>>

    @Transaction
    @Query(
        """
        SELECT * FROM markers 
            INNER JOIN photo_collections ON markerColorCode = colorCode
            WHERE colorCode = :colorCode     
    """
    )
    fun getPhotoCollectionsByMarkerColorCode(colorCode: String): Flow<List<CollectionWithPhotosRelation>>

    @Transaction
    @Query(
        """
        SELECT * FROM photo_collections pc
            INNER JOIN markers m ON pc.markerColorCode = m.colorCode
            INNER JOIN photos p ON p.photoCollectionId = pc.id
            WHERE mapPoint = :point LIMIT 1
    """
    )
    fun getFullCollectionByLocation(point: String): Flow<FullCollectionRelation?>
}

