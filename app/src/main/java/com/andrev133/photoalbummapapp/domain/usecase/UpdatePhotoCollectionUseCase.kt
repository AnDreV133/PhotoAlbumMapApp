package com.andrev133.photoalbummapapp.domain.usecase

import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.toPhotoCollectionEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpdatePhotoCollectionUseCase( // TODO: not for using
    private val dao: PhotoCollectionMarkerDao,
    private val coroutineScope: CoroutineScope
) {
    operator fun invoke(
        model: PhotoCollectionMarkerModel,
    ) {
        coroutineScope.launch {
            dao.updateCollection(model.toPhotoCollectionEntity())
        }
    }
}