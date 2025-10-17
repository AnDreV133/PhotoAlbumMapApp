package com.andrev133.photoalbummapapp.app.compose

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.andrev133.photoalbummapapp.app.compose.elem.MarkerRowItem
import com.andrev133.photoalbummapapp.app.compose.elem.MarkerRowItemForInput
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel


@Composable
fun MarkerListDialog(
    onDismiss: () -> Unit,
    onCompleteMarker: (PhotoCollectionMarkerModel) -> Unit,
    models: List<PhotoCollectionMarkerModel>
) {
    var selectedModel by remember { mutableStateOf<PhotoCollectionMarkerModel?>(null) }

    BackHandler(onBack = onDismiss)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .fillMaxHeight(0.75f)
                .padding(16.dp),
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MarkerRowItemForInput(
                    editableModel = selectedModel,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onComplete = {
                        onCompleteMarker(it)
                        selectedModel = null
                    }
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = MaterialTheme.shapes.large
                        )
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(models) {
                        MarkerRowItem(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                            model = it,
                            onClick = { selectedModel = it })
                    }
                }
            }
        }
    }
}

@Preview(device = "spec:width=1080px,height=1080px,dpi=440")
@Composable
fun ExampleUsage() {
    val collections = remember {
        listOf(
            PhotoCollectionMarkerModel(Color.Red, "Отпуск 2024"),
            PhotoCollectionMarkerModel(Color.Yellow, "Отпуск 2023"),
            PhotoCollectionMarkerModel(Color.Blue, "Отпуск 2022"),
        )
    }
    AutoMediaAppTheme {
        Box(modifier = Modifier.size(600.dp)) {
            MarkerListDialog(
                onDismiss = { },
                onCompleteMarker = {
                    println("Добавлена коллекция: ${it.title}, цвет: ${it.color}")
                },
                models = collections
            )

        }
    }
}

