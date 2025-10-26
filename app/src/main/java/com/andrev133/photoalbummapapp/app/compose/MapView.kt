package com.andrev133.photoalbummapapp.app.compose

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.app.compose.elem.CustomAlertDialog
import com.andrev133.photoalbummapapp.app.compose.util.distanceTo
import com.andrev133.photoalbummapapp.data.SharedPrefKeys
import com.andrev133.photoalbummapapp.data.getSharedPrefByKey
import com.andrev133.photoalbummapapp.data.setValue
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import ru.sulgik.mapkit.compose.CameraPositionState
import ru.sulgik.mapkit.compose.MapEffect
import ru.sulgik.mapkit.compose.Placemark
import ru.sulgik.mapkit.compose.PlacemarkState
import ru.sulgik.mapkit.compose.YandexMap
import ru.sulgik.mapkit.compose.YandexMapsComposeExperimentalApi
import ru.sulgik.mapkit.geometry.Point
import ru.sulgik.mapkit.map.CameraListener
import ru.sulgik.mapkit.map.CameraPosition
import ru.sulgik.mapkit.map.CameraUpdateReason
import ru.sulgik.mapkit.map.InputListener
import ru.sulgik.mapkit.map.Map


@OptIn(YandexMapsComposeExperimentalApi::class)
@Composable
fun MapView( // fixme recompose twice
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    collectionMarkers: List<PhotoCollectionMarkerModel>,
    markers: List<PhotoCollectionMarkerModel>, // todo call recompose, but its not needed
    onAddMarker: (PhotoCollectionMarkerModel) -> Unit,
    onRemoveMarker: (PhotoCollectionMarkerModel) -> Unit,
    onClickMarker: (PhotoCollectionMarkerModel) -> Unit
) {
    val context = LocalContext.current

    var pointOnShowMarkerListDialog by remember { mutableStateOf<Point?>(null) }
    var modelForShowAlertDialog by remember { mutableStateOf<PhotoCollectionMarkerModel?>(null) }

    val cameraListener by remember {
        derivedStateOf {
            object : CameraListener() {
                override fun onCameraPositionChanged(
                    map: Map,
                    cameraPosition: CameraPosition,
                    cameraUpdateReason: CameraUpdateReason,
                    finished: Boolean
                ) {
                    println("cameraPosition: $cameraPosition")
                }
            }
        }
    }
    val inputListener by remember {
        derivedStateOf {
            object : InputListener() {
                override fun onMapTap(map: Map, point: Point) {
                    Log.d("MapView", "onMapTap point: $point")
                    pointOnShowMarkerListDialog = point
                }

                override fun onMapLongTap(map: Map, point: Point) {}
            }
        }
    }

    if (pointOnShowMarkerListDialog != null) {
        MarkerListDialog(
            onDismiss = { pointOnShowMarkerListDialog = null },
            onClickItem = {
                onAddMarker(
                    it.copy(
                        point = pointOnShowMarkerListDialog!!
                    )
                )
                pointOnShowMarkerListDialog = null
            },
            models = markers
        )
    }

    if (modelForShowAlertDialog != null) {
        CustomAlertDialog(
            title = "Удалить маркер?\nФотографии больше не будут ассоциированы с ним.",
            confirmText = "Удалить",
            dismissText = "Отмена",
            onConfirm = {
                onRemoveMarker(modelForShowAlertDialog!!)
                modelForShowAlertDialog = null
            },
            onDismiss = {
                modelForShowAlertDialog = null
            }
        )
    }

    YandexMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState
    ) {
        MapEffect { map ->
            map.addCameraListener(cameraListener)
            map.addInputListener(inputListener)
        }

        collectionMarkers.forEach { model ->
            Placemark(
                opacity = 0.8f,
                zIndex = 1000f,
                onTap = {
                    onClickMarker(model)
                    true
                },
                state = PlacemarkState(
                    geometry = model.point,
                ),
                contentSize = DpSize(24.dp, 24.dp),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_marker),
                    tint = model.color,
                    contentDescription = model.title,
                )
            }
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