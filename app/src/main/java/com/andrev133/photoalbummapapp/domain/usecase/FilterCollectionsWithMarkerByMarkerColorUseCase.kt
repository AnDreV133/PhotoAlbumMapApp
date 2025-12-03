package com.andrev133.photoalbummapapp.domain.usecase

import androidx.compose.ui.graphics.Color
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel

class FilterCollectionsWithMarkerByMarkerColorUseCase {
    operator fun invoke(
        models: List<PhotoCollectionMarkerModel>,
        markerColor: Color
    ): List<PhotoCollectionMarkerModel> =
        models
            .filter { it.color == markerColor }
            .sortedBy { it.time }
}
