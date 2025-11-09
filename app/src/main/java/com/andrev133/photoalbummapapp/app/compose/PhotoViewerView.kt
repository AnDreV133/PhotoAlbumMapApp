package com.andrev133.photoalbummapapp.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.andrev133.photoalbummapapp.app.compose.elem.getFileManagerLauncher
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel
import com.andrev133.photoalbummapapp.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/*
@Composable
fun PhotoViewerView(
    modifier: Modifier = Modifier,
    model: PhotoCollectionMarkerModel,
    photosFlow: Flow<List<PhotoModel>>,
    onClose: () -> Unit,
    onInsertPhotos: (List<String?>) -> Unit,
    onDeletePhoto: (List<String>) -> Unit
) {
    var currentScale by remember { mutableStateOf(1f) }
    var currentOffsetX by remember { mutableStateOf(0f) }
    var currentOffsetY by remember { mutableStateOf(0f) }
    val photos by photosFlow.collectAsState(model.photos.toList())

    val fileMangerLauncher = getFileManagerLauncher {
        onInsertPhotos(it.map { uri -> uri.path })
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { photos.size }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            currentScale = (currentScale * zoom).coerceIn(1f, 5f)

                            // Сброс трансформаций при смене страницы
                            if (page != pagerState.currentPage) {
                                currentScale = 1f
                                currentOffsetX = 0f
                                currentOffsetY = 0f
                            }
                        }
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = photos[page]),
                    contentDescription = "Фотография $page",
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = currentScale
                            scaleY = currentScale
                            translationX = currentOffsetX
                            translationY = currentOffsetY
                        }
                        .pointerInput(currentScale) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                if (currentScale > 1f) {
                                    val newScale = currentScale * zoom
                                    currentScale = newScale.coerceIn(1f, 5f)

                                    // Ограничение перемещения при зуме
                                    val maxOffset = (currentScale - 1) * size.width / 2
                                    currentOffsetX = (currentOffsetX + pan.x * currentScale)
                                        .coerceIn(-maxOffset, maxOffset)
                                    currentOffsetY = (currentOffsetY + pan.y * currentScale)
                                        .coerceIn(-maxOffset, maxOffset)
                                } else {
                                    // Сброс смещения при масштабе 1:1
                                    currentOffsetX = 0f
                                    currentOffsetY = 0f
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
                        fileMangerLauncher()
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
                            onDeletePhoto(
                                listOf(photos[pagerState.currentPage].path)
                            )
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
*/

@Composable
fun PhotoViewerView(
    modifier: Modifier = Modifier,
    model: PhotoCollectionMarkerModel,
    photosFlow: Flow<List<PhotoModel>>,
    onClose: () -> Unit,
    onInsertPhotos: (List<String?>) -> Unit,
    onDeletePhoto: (List<String>) -> Unit
) {
    val scope = rememberCoroutineScope()

    var currentScale by remember { mutableStateOf(1f) }
    var currentOffsetX by remember { mutableStateOf(0f) }
    var currentOffsetY by remember { mutableStateOf(0f) }
    var selectedPhotosForDeletion by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var showFileManager by remember { mutableStateOf(false) }

    val fileMangerLauncher = getFileManagerLauncher {
        onInsertPhotos(it.map { uri -> "content://media${uri.path}" })
    }

    // Автоматический запуск файлового менеджера когда showFileManager = true
    LaunchedEffect(showFileManager) {
        if (showFileManager) {
            fileMangerLauncher()
            showFileManager = false
        }
    }

    val photos by photosFlow.collectAsState(model.photos.toList())

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { photos.size }
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val isSelectedForDeletion = selectedPhotosForDeletion.contains(page)

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Фон выделения для выбранных фото
                if (isSelectedForDeletion) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red.copy(alpha = 0.3f))
                    )
                }

                AsyncImage(
                    model = photos[page].path,
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
                            detectVerticalDragGestures { change, dragAmount ->
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
                        }
                        /*.pointerInput(currentScale, selectedPhotosForDeletion.isNotEmpty()) {
                            detectTransformGestures { _, pan, zoom, _ ->
                                currentScale = (currentScale * zoom).coerceIn(1f, 5f)

                                // Сброс трансформаций при смене страницы
                                if (page != pagerState.currentPage) {
                                    currentScale = 1f
                                    currentOffsetX = 0f
                                    currentOffsetY = 0f
                                }

                                val newScale = currentScale * zoom
                                currentScale = newScale.coerceIn(1f, 5f)

                                // Ограничение перемещения при зуме
                                val maxOffset = (currentScale - 1) * size.width / 2
                                currentOffsetX = (currentOffsetX + pan.x * currentScale)
                                    .coerceIn(-maxOffset, maxOffset)
                                currentOffsetY = (currentOffsetY + pan.y * currentScale)
                                    .coerceIn(-maxOffset, maxOffset)

                                if (currentScale < 1f) {
                                    currentScale = 1f
                                    currentOffsetX = 0f
                                    currentOffsetY = 0f
                                }
                            }
                        }*/
                        .pointerInput(page, selectedPhotosForDeletion.isNotEmpty()) {
                            detectTapGestures {
                                // При обычном тапе сбрасываем выделение, если есть выбранные фото
                                if (selectedPhotosForDeletion.isNotEmpty()) {
                                    selectedPhotosForDeletion = emptySet()
                                }
                            }
                        },
                    contentScale = ContentScale.Fit
                )

                // Показываем номер выбранного фото в режиме выбора
                if (isSelectedForDeletion) {
                    Text(
                        text = "${selectedPhotosForDeletion.indexOf(page) + 1}",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .background(
                                color = Color.Red.copy(alpha = 0.8f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

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
                onClick = {
                    if (selectedPhotosForDeletion.isNotEmpty()) {
                        // Сбрасываем выделение при нажатии на кнопку закрытия
                        selectedPhotosForDeletion = emptySet()
                    } else {
                        onClose()
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = Color.Black.copy(alpha = 0.6f),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = if (selectedPhotosForDeletion.isNotEmpty()) "Отменить выбор" else "Закрыть",
                    tint = Color.White
                )
            }

            Row {
                // Кнопка добавления фото показывается только когда нет выбранных для удаления
                if (selectedPhotosForDeletion.isEmpty()) {
                    IconButton(
                        onClick = {
                            showFileManager = true
                        }, // Исправлено: устанавливаем флаг вместо прямого вызова
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
                }

                // Кнопка удаления показывается когда есть выбранные фото ИЛИ когда нет выбранных но есть фото
                if (selectedPhotosForDeletion.isNotEmpty() ||
                    (selectedPhotosForDeletion.isEmpty() && photos.isNotEmpty())
                ) {

                    IconButton(
                        onClick = {
                            if (selectedPhotosForDeletion.isNotEmpty()) {
                                // Удаляем выбранные фото
                                val pathsToDelete =
                                    selectedPhotosForDeletion.map { photos[it].path }
                                onDeletePhoto(pathsToDelete)
                                selectedPhotosForDeletion = emptySet()
                            } else {
                                // Если нет выбранных, переходим в режим выбора (выбираем текущее фото)
                                selectedPhotosForDeletion = setOf(pagerState.currentPage)
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(
                                color = if (selectedPhotosForDeletion.isNotEmpty())
                                    Color.Red.copy(alpha = 0.8f)
                                else
                                    Color.Black.copy(alpha = 0.6f),
                                shape = MaterialTheme.shapes.small
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = if (selectedPhotosForDeletion.isNotEmpty())
                                "Удалить выбранные (${selectedPhotosForDeletion.size})"
                            else
                                "Выбрать для удаления",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        // Индикатор выбранных фото
        if (selectedPhotosForDeletion.isNotEmpty()) {
            Text(
                text = "Выбрано: ${selectedPhotosForDeletion.size}",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 16.dp)
                    .background(
                        color = Color.Red.copy(alpha = 0.8f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        } else if (photos.size > 1) {
            // Обычный индикатор страниц когда нет выбора
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

    PhotoViewerView(
        model = sampleModel,
        onClose = { },
        onInsertPhotos = { },
        onDeletePhoto = { },
        photosFlow = flowOf(),
        modifier = Modifier.fillMaxSize()
    )
}