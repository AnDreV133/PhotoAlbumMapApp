package com.andrev133.photoalbummapapp.domain.usecase

import com.andrev133.photoalbummapapp.data.dao.PhotoCollectionMarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.toMarker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllMarkers(private val dao: PhotoCollectionMarkerDao) {
    operator fun invoke(): Flow<List<PhotoCollectionMarkerModel>> =
        dao.getAllCollections()
            .map { list ->
                list.map {
                    it.toMarker()
                }
            }
}