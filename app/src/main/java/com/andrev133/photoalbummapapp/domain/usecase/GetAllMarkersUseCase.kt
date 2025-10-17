package com.andrev133.photoalbummapapp.domain.usecase

import com.andrev133.photoalbummapapp.data.dao.MarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllMarkersUseCase(private val dao: MarkerDao) {
    operator fun invoke(): Flow<List<PhotoCollectionMarkerModel>> =
        dao.getAllMarkers()
            .map { list ->
                list.map {
                    it.toModel()
                }
            }
}