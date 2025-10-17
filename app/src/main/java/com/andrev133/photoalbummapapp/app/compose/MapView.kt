package com.andrev133.photoalbummapapp.app.compose

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.data.SharedPrefKeys
import com.andrev133.photoalbummapapp.data.getSharedPrefByKey
import com.andrev133.photoalbummapapp.data.setValue
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.usecase.GetAllMarkersUseCase
import ru.sulgik.mapkit.compose.Placemark
import ru.sulgik.mapkit.compose.PlacemarkState
import ru.sulgik.mapkit.compose.YandexMap
import ru.sulgik.mapkit.compose.YandexMapsComposeExperimentalApi
import ru.sulgik.mapkit.compose.rememberCameraPositionState
import ru.sulgik.mapkit.geometry.Point
import ru.sulgik.mapkit.map.CameraPosition


@OptIn(YandexMapsComposeExperimentalApi::class)
@Composable
fun MapView(
    modifier: Modifier = Modifier,
    getAllMarkersUseCase: GetAllMarkersUseCase,

    ) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState { }
    var markerPositionList = remember { listOf<PhotoCollectionMarkerModel>() }

    YandexMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        markerPositionList.forEach { model ->
            Placemark(
                state = PlacemarkState(
                    geometry = Point(model.point.latitude, model.point.longitude),
                ),
                contentSize = DpSize(32.dp, 32.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_marker),
                    tint = model.color,
                    contentDescription = model.title,
                )
            }
        }

    }

    LaunchedEffect(Unit) {
        getAllMarkersUseCase().collect { marker ->
            markerPositionList = marker
        }
    }

    DisposableEffect(Unit) {
        val sharedPref = context.getSharedPrefByKey(SharedPrefKeys.MAIN_SETTINGS)

        val latitudeKey = "lastCameraPositionLatitude"
        val longitudeKey = "lastCameraPositionLongitude"

        cameraPositionState.position = CameraPosition(
            target = Point(
                (sharedPref.getFloat(latitudeKey, 50.5957f)).toDouble(),
                (sharedPref.getFloat(longitudeKey, 36.5872f)).toDouble()
            ),
            zoom = 10.0f,
            azimuth = 0.0f,
            tilt = 0.0f
        )

        onDispose {
            cameraPositionState.position.let { camPos ->
                sharedPref.setValue(latitudeKey, camPos.target.latitude)
                sharedPref.setValue(longitudeKey, camPos.target.longitude)
            } // FIXME: current camera position not saved
        }
    }
}