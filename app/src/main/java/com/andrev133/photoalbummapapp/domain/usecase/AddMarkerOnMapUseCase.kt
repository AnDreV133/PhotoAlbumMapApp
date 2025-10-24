package com.andrev133.photoalbummapapp.domain.usecase

import android.database.sqlite.SQLiteException
import android.util.Log
import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.toPhotoCollectionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddMarkerOnMapUseCase(
    private val dao: PhotoCollectionMarkerDao,
    private val coroutineScope: CoroutineScope
) {
    operator fun invoke(model: PhotoCollectionMarkerModel) {
        coroutineScope.launch {
            try {
                dao.insertCollection(model.toPhotoCollectionEntity())
            } catch (e: SQLiteException) {
                Log.e("AddMarkerOnMapUseCase", e.message ?: "")
            }
        }
    }
}
