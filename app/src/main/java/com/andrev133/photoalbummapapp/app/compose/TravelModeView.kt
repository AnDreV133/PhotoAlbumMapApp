package com.andrev133.photoalbummapapp.app.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import ru.sulgik.mapkit.compose.CameraPositionState

@Composable
fun TravelModeView(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    markerColor: Color,
    models: List<PhotoCollectionMarkerModel>,
    onClose: () -> Unit,
) {
    var currentModelIndex by remember { mutableIntStateOf(0) }

    BackHandler(onBack = onClose)

    LaunchedEffect(Unit) {
        cameraPositionState.position = cameraPositionState.position.copy(
            target = models[currentModelIndex].point
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Кнопка закрытия слева сверху
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .size(48.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = MaterialTheme.shapes.small
                )
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Закрыть",
                tint = Color.White
            )
        }

        // Индикатор цвета маркера справа сверху
        Icon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .size(48.dp)
                .background(
                    color = Color.Black,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_marker),
            tint = markerColor,
            contentDescription = "changed marker color"
        )

        // Кнопки навигации снизу
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Кнопка "Вверх"
            IconButton(
                onClick = {
                    if (currentModelIndex + 1 < models.size) {
                        currentModelIndex++
                        cameraPositionState.position = cameraPositionState.position.copy(
                            target = models[currentModelIndex].point
                        )
                    }
                },

                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = "Переместить вверх",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }

            // Кнопка "Вниз"
            IconButton(
                onClick = {
                    if (currentModelIndex - 1 >= 0) {
                        currentModelIndex--
                        cameraPositionState.position = cameraPositionState.position.copy(
                            target = models[currentModelIndex].point
                        )
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(64.dp)
                    .padding(horizontal = 16.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Переместить вниз",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun TravelModeViewPreview() {
    MaterialTheme {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.DarkGray)
//        ) {
//            TravelModeView(
//                markerColor = Color.Red,
//                currentModel = PhotoCollectionMarkerModel(
//                    color = Color.Red,
//                    title = "Тестовый маркер",
//                    photos = emptySet()
//                ),
//                onClose = { },
//            )
//        }
    }
}