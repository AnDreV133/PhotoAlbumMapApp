package com.andrev133.photoalbummapapp.domain.usecase

import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.PhotoModel
import kotlinx.coroutines.flow.map

class GetAllPhotosUseCase(
    private val dao: PhotoCollectionMarkerDao
) {
    operator fun invoke(model: PhotoCollectionMarkerModel) =
        dao.getPhotosByCollectionId(model.id)
            .map { list ->
                list.map {
                    PhotoModel(it.path)
                }
            }
}