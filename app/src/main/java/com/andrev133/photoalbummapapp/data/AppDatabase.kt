package com.andrev133.photoalbummapapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.data.dao.PhotoDao
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionMarkerEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoEntity

@Database(
    entities = [
        PhotoCollectionMarkerEntity::class,
        PhotoEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoCollectionDao(): PhotoCollectionMarkerDao
    abstract fun photoDao(): PhotoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "photo_collection_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}