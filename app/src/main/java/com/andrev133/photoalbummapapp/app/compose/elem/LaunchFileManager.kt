package com.andrev133.photoalbummapapp.app.compose.elem

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun getFileManagerLauncher(
    mimeType: String = "image/*",
    onResult: (List<Uri>) -> Unit
): () -> Unit {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris -> onResult(uris) }
    )

    return { launcher.launch(mimeType) }
}