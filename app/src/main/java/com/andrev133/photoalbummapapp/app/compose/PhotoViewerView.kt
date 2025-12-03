package com.andrev133.photoalbummapapp.app.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.andrev133.photoalbummapapp.app.compose.elem.getFileManagerLauncher
import com.andrev133.photoalbummapapp.app.compose.util.serialize
import com.andrev133.photoalbummapapp.app.compose.util.toUri
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
fun PhotoViewerView(
    modifier: Modifier = Modifier,
    model: PhotoCollectionMarkerModel,
    getPhotoModelFlow: (PhotoCollectionMarkerModel) -> Flow<List<PhotoModel>>,
    onInsertPhotos: (List<String?>) -> Unit,
    onDeletePhoto: (List<String>) -> Unit,
    onClose: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var currentScale by remember { mutableFloatStateOf(1f) }
    var currentOffsetX by remember { mutableFloatStateOf(0f) }
    var currentOffsetY by remember { mutableFloatStateOf(0f) }
    var showFileManager by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    BackHandler(onBack = onClose)

    val fileMangerLauncher = getFileManagerLauncher {
        onInsertPhotos(
            it.map { uri -> uri.serialize() }
//            it.map { uri -> "content://media${uri.path}" }
        )
    }

    LaunchedEffect(showFileManager) {
        if (showFileManager) {
            fileMangerLauncher()
            showFileManager = false
        }
    }

    val photos by getPhotoModelFlow(model).collectAsState(model.photos.toList())

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { photos.size }
    )

    // Диалоговое окно подтверждения удаления
    if (showDeleteDialog) {
        PhotoDeleteAlertDialog(
            onConfirm = {
                onDeletePhoto(listOf(photos[pagerState.currentPage].path))
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = photos[page].path.toUri()
                    ),
                    contentDescription = "Фотография $page",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = currentScale
                            scaleY = currentScale
                            translationX = currentOffsetX
                            translationY = currentOffsetY
                        }
                        .pointerInput(Unit) {
                            detectVerticalDragGestures { _, dragAmount ->
                                if (dragAmount > 20) { // Порог срабатывания жеста
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                    }
                                } else if (dragAmount < -20) {
                                    scope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                        },
                    contentScale = ContentScale.Fit
                )

                if (currentScale > 1f) {
                    IconButton(
                        onClick = {
                            currentScale = 1f
                            currentOffsetX = 0f
                            currentOffsetY = 0f
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 80.dp)
                            .size(48.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Сбросить масштаб",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopStart),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier
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

            Row {
                IconButton(
                    onClick = {
                        showFileManager = true
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f),
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Добавить фото",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                if (photos.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = Color.Black.copy(alpha = 0.6f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Удалить фото",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        if (photos.size > 1) {
            Text(
                text = "${pagerState.currentPage + 1}/${photos.size}",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun PhotoDeleteAlertDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.surface,
        onDismissRequest = onDismiss,
        title = {
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                text = "Удаление фотографии"
            )
        },
        text = {
            Text(
                color = MaterialTheme.colorScheme.onSurface,
                text = "Вы уверены, что хотите удалить эту фотографию?"
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismiss()
                }
            ) {
                Text("Удалить", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Отмена", color = MaterialTheme.colorScheme.onSurface)
            }
        }
    )
}

@Preview(backgroundColor = 0xFF7B1FA2)
@Composable
fun PhotoViewerViewPreview() {
    val sampleModel = remember {
        PhotoCollectionMarkerModel(
            color = Color.Red,
            title = "Выпускной",
            photos = setOf(
            ),
        )
    }

    MaterialTheme {
        PhotoViewerView(
            model = sampleModel,
            onClose = { },
            onInsertPhotos = { },
            onDeletePhoto = { },
            getPhotoModelFlow = { flowOf() },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun PhotoDeleteAlertDialogPreview() {
    MaterialTheme {
        PhotoDeleteAlertDialog({}, {})
    }
}