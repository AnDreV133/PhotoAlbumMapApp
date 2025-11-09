package com.andrev133.photoalbummapapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andrev133.photoalbummapapp.BuildConfig
import com.andrev133.photoalbummapapp.app.compose.FooterBar
import com.andrev133.photoalbummapapp.app.compose.MapView
import com.andrev133.photoalbummapapp.app.compose.MarkerListDialogWithAdd
import com.andrev133.photoalbummapapp.app.compose.PhotoViewerView
import com.andrev133.photoalbummapapp.app.compose.elem.RequestCoarseLocationPermission
import com.andrev133.photoalbummapapp.app.compose.elem.RequestReadStoragePermission
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme
import com.andrev133.photoalbummapapp.data.AppDatabase
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.usecase.AddMarkerOnMapUseCase
import com.andrev133.photoalbummapapp.domain.usecase.AddPhotosUseCase
import com.andrev133.photoalbummapapp.domain.usecase.DeleteMarkerOnMapUseCase
import com.andrev133.photoalbummapapp.domain.usecase.DeletePhotosUseCase
import com.andrev133.photoalbummapapp.domain.usecase.GetAllCollectionsWithMarkerUseCase
import com.andrev133.photoalbummapapp.domain.usecase.GetAllMarkersUseCase
import com.andrev133.photoalbummapapp.domain.usecase.GetAllPhotosUseCase
import com.andrev133.photoalbummapapp.domain.usecase.InsertMarkerUseCase
import com.andrev133.photoalbummapapp.domain.usecase.UpdatePhotoCollectionUseCase
import ru.sulgik.mapkit.MapKit
import ru.sulgik.mapkit.compose.bindToLifecycleOwner
import ru.sulgik.mapkit.compose.rememberAndInitializeMapKit
import ru.sulgik.mapkit.compose.rememberCameraPositionState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKit.setApiKey(BuildConfig.MAP_API_KEY)
        MapKit.initialize(this)

        enableEdgeToEdge()
        setContent {
            AutoMediaAppTheme {
                RequestReadStoragePermission()
                RequestCoarseLocationPermission()

                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    rememberAndInitializeMapKit().bindToLifecycleOwner()

    val coroutineScope = rememberCoroutineScope()

    val db = AppDatabase.getInstance(LocalContext.current)

    val getAllMarkersUseCase = GetAllMarkersUseCase(db.markerDao())
    val getAllCollectionsWithMarkerUseCase =
        GetAllCollectionsWithMarkerUseCase(db.photoCollectionDao())
    val insertMarkerUseCase = InsertMarkerUseCase(db.markerDao(), coroutineScope)
    val addMarkerOnMapUseCase = AddMarkerOnMapUseCase(db.photoCollectionDao(), coroutineScope)
    val deleteMarkerUseCase = DeleteMarkerOnMapUseCase(db.photoCollectionDao(), coroutineScope)
    val updatePhotoCollectionUseCase =
        UpdatePhotoCollectionUseCase(db.photoCollectionDao(), coroutineScope)
    val addPhotosUseCase = AddPhotosUseCase(db.photoCollectionDao(), coroutineScope)
    val deletePhotosUseCase = DeletePhotosUseCase(db.photoCollectionDao(), coroutineScope)
    val getAllPhotosUseCase = GetAllPhotosUseCase(db.photoCollectionDao())

    val cameraPositionState = rememberCameraPositionState()
    val markers by getAllMarkersUseCase()
        .collectAsStateWithLifecycle(emptyList())
    val collectionMarkers by getAllCollectionsWithMarkerUseCase()
        .collectAsStateWithLifecycle(emptyList())

    var showMarkerListDialogWithAdd by remember { mutableStateOf(false) }
    var photoCollectionForShowPhotoViewer by remember {
        mutableStateOf<PhotoCollectionMarkerModel?>(null)
    }

    if (showMarkerListDialogWithAdd) {
        MarkerListDialogWithAdd(
            onDismiss = { showMarkerListDialogWithAdd = false },
            onCompleteMarker = { model -> insertMarkerUseCase(model) },
            models = markers
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            MapView(
                modifier = Modifier
                    .padding(innerPadding),
                collectionMarkers = collectionMarkers,
                cameraPositionState = cameraPositionState,
                onAddMarker = { addMarkerOnMapUseCase(it) },
                onRemoveMarker = { deleteMarkerUseCase(it) },
                onClickMarker = { photoCollectionForShowPhotoViewer = it },
                markers = markers
            )
        },
        bottomBar = {
            FooterBar(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                onMarkerListClick = { showMarkerListDialogWithAdd = true },
                onCentralClick = {},
                onTravelClick = {}
            )
        }
    )

    if (photoCollectionForShowPhotoViewer != null) {
        PhotoViewerView(
            modifier = Modifier.fillMaxSize(),
            model = photoCollectionForShowPhotoViewer!!,
            photosFlow = getAllPhotosUseCase(photoCollectionForShowPhotoViewer!!),
            onClose = { photoCollectionForShowPhotoViewer = null },
            onInsertPhotos = {
                addPhotosUseCase(
                    photoCollectionForShowPhotoViewer!!,
                    it
                )
            }, // TODO insert photos by index
            onDeletePhoto = {
                deletePhotosUseCase(
                    photoCollectionForShowPhotoViewer!!,
                    it
                )
            }
        )
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    AutoMediaAppTheme {
        MainScreen()
    }
}