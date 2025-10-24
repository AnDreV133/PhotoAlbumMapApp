package com.andrev133.photoalbummapapp.app.compose.elem

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme

@Composable
fun CustomAlertDialog(
    title: String,
    confirmText: String,
    dismissText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                fontSize = 20.sp,
                text = title
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(dismissText)
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(confirmText)
            }
        }
    )
}

@Preview
@Composable
fun CustomAlertDialogPreview() {
    AutoMediaAppTheme {
        CustomAlertDialog(
            title = "TitleTitleTitleTitle\nTitleTitle",
            confirmText = "Confirm",
            dismissText = "Dismiss",
            onDismiss = {},
            onConfirm = {}
        )
    }
}