package com.andrev133.photoalbummapapp.domain.usecase

import androidx.compose.ui.graphics.toArgb
import com.andrev133.photoalbummapapp.data.dao.MarkerDao
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.toMarkerEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InsertMarkerUseCase(
    private val dao: MarkerDao,
    private val coroutineScope: CoroutineScope
) {
    operator fun invoke(model: PhotoCollectionMarkerModel) {
        coroutineScope.launch(
            context = coroutineScope.coroutineContext + Dispatchers.IO
        ) {
            if (model.title.isBlank()) {
                dao.deleteByColorCode(model.color.toArgb())
            } else {
                dao.upsert(model.toMarkerEntity())
            }
        }
    }
}
