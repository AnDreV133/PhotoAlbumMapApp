package com.andrev133.photoalbummapapp.domain.usecase

import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.PhotoModel
import com.andrev133.photoalbummapapp.domain.model.toModel
import com.andrev133.photoalbummapapp.domain.model.toPhotosEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddPhotosUseCase(
    private val dao: PhotoCollectionMarkerDao,
    private val coroutineScope: CoroutineScope
) {
    operator fun invoke(
        model: PhotoCollectionMarkerModel,
        photoPaths: List<String?>
    ) {
        coroutineScope.launch {
            val clearedPaths = photoPaths
                .filterNotNull()
                .map { path -> PhotoModel(path) }

            val newPhotos = dao.getFullCollectionById(model.id)
                .toModel()
                .photos + clearedPaths.toSet()

            dao.updatePhotos(
                model.id,
                model
                    .copy(photos = newPhotos)
                    .toPhotosEntity()
            )
        }
    }
}