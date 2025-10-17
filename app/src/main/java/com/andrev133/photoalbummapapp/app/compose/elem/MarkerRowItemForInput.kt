package com.andrev133.photoalbummapapp.app.compose.elem

// Для BackHandler (если используете)

// Для GridCells (если используете LazyVerticalGrid)
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme
import com.andrev133.photoalbummapapp.app.ui.theme.endRoundedRect
import com.andrev133.photoalbummapapp.app.ui.theme.startRoundedRect
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel

@Composable
fun MarkerRowItemForInput(
    modifier: Modifier = Modifier,
    onComplete: (PhotoCollectionMarkerModel) -> Unit,
    editableModel: PhotoCollectionMarkerModel? = null
) {
    var currentColor by remember { mutableStateOf(Color.Gray) }
    var currentTitle by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .padding(4.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(elevation = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ColorPickerIconButton(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color.Black,
                    shape = MaterialTheme.shapes.startRoundedRect
                ),
            onColorSelected = { currentColor = it },
        ) {
            Icon(
                modifier = Modifier.padding(8.dp),
                painter = painterResource(R.drawable.ic_marker),
                contentDescription = "Выбор цвета",
                tint = currentColor
            )
        }

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(60.dp)
                .weight(1f)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            Color.Black,
                            MaterialTheme.colorScheme.primary,
                        ),
                        endX = 40f
                    ),
                    shape = MaterialTheme.shapes.endRoundedRect
                ),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 8.dp),
                value = currentTitle,
                onValueChange = { currentTitle = it },
                placeholder = {
                    Text(
                        modifier = Modifier,
                        text = "Название...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f),
                        )
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        val newModel = PhotoCollectionMarkerModel(
                            title = currentTitle.trim(),
                            color = currentColor
                        )
                        onComplete(newModel)

                        currentColor = Color.Gray
                        currentTitle = ""
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }
    }

    LaunchedEffect(editableModel) {
        editableModel?.let {
            currentTitle = it.title
            currentColor = it.color
        }
    }
}

@Composable
fun ColorPickerIconButton(
    modifier: Modifier = Modifier,
    onColorSelected: (Color) -> Unit,
    contentIcon: @Composable () -> Unit
) {
    var showColorPicker by remember { mutableStateOf(false) }

    IconButton(
        modifier = modifier,
        onClick = { showColorPicker = true },
        content = contentIcon
    )

    if (showColorPicker)
        ColorPickerDialog(
            onDismiss = { showColorPicker = false },
            onColorSelected = {
                onColorSelected(it)
                showColorPicker = false
            }
        )
}

@Composable
fun ColorPickerDialog(
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    val colors = listOf(
        Color.Red,
        Color.Green,
        Color.Blue,
        Color.Yellow,
        Color.Magenta,
        Color.Cyan,
        Color(0xFFFF9800), // Orange
        Color(0xFF9C27B0), // Purple
        Color(0xFF795548), // Brown
        Color(0xFF607D8B)  // Blue Grey
    )

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Выберите цвет",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.size(200.dp)
                ) {
                    items(colors) { color ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(color, CircleShape)
                                .clickable { onColorSelected(color) }
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    shape = CircleShape
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MarkerRowItemForInputPreview() {
    AutoMediaAppTheme {
        MarkerRowItemForInput(
            onComplete = {},
        )
    }
}
//        IconButton(
//            onClick = {
//                if (collectionName.isNotBlank()) {
//                    val newModel = PhotoCollectionMarkerModel(
//                        title = collectionName,
//                        color = selectedColor
//                    )
//                    onAddCollection(newModel)
//                    if (clearAfterInput) {
//                        collectionName = ""
//                    }
//                }
//            },
//            enabled = collectionName.isNotBlank(),
//            modifier = Modifier
//                .size(56.dp)
//                .background(
//                    color = if (collectionName.isNotBlank()) MaterialTheme.colorScheme.primary
//                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
//                    shape = CircleShape
//                )
//        ) {
//            Icon(
//                painter = painterResource(R.drawable.ic_plus),
//                contentDescription = "Добавить коллекцию",
//                tint = if (collectionName.isNotBlank()) MaterialTheme.colorScheme.onPrimary
//                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
//            )
//        }
