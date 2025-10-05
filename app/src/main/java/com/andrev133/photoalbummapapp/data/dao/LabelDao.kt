package com.andrev133.photoalbummapapp.data.dao

import androidx.room.*
import com.andrev133.photoalbummapapp.data.entity.LabelEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabel(label: LabelEntity)

    @Update
    suspend fun updateLabel(label: LabelEntity)

    @Delete
    suspend fun deleteLabel(label: LabelEntity)

    @Query("SELECT * FROM labels")
    fun getAllLabels(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels WHERE colorCode = :colorCode")
    suspend fun getLabelByColorCode(colorCode: String): LabelEntity?

    @Query("SELECT * FROM labels WHERE name LIKE :name LIMIT 1")
    suspend fun findLabelByName(name: String): LabelEntity?
}

