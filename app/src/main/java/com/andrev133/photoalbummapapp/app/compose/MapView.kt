package com.andrev133.photoalbummapapp.app.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.andrev133.photoalbummapapp.data.SharedPrefKeys
import com.andrev133.photoalbummapapp.data.getSharedPrefByKey
import com.andrev133.photoalbummapapp.data.setValue
import ru.sulgik.mapkit.compose.YandexMap
import ru.sulgik.mapkit.compose.rememberCameraPositionState
import ru.sulgik.mapkit.geometry.Point
import ru.sulgik.mapkit.map.CameraPosition


@Composable
fun MapView(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState { }

    YandexMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    )

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