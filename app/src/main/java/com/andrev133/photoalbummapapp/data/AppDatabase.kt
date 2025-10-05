package com.andrev133.photoalbummapapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andrev133.photoalbummapapp.data.dao.LabelDao
import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionDao
import com.andrev133.photoalbummapapp.data.dao.PhotoPathDao
import com.andrev133.photoalbummapapp.data.entity.LabelEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoCollectionEntity
import com.andrev133.photoalbummapapp.data.entity.PhotoPathEntity

@Database(
    entities = [
        LabelEntity::class,
        PhotoCollectionEntity::class,
        PhotoPathEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun labelDao(): LabelDao
    abstract fun photoCollectionDao(): PhotoCollectionDao
    abstract fun photoPathDao(): PhotoPathDao

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