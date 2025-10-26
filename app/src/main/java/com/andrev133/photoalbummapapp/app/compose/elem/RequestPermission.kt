package com.andrev133.photoalbummapapp.app.compose.elem

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun RequestCoarseLocationPermission() {
    val context = LocalContext.current
    var hasLocationPermission by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasLocationPermission = isGranted
    }

    LaunchedEffect(Unit) {
        hasLocationPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    showAlertDialog = !hasLocationPermission

    CustomAlertDialog(
        title = "Запрос разрешения на использование геолокации",
        confirmText = "Разрешить",
        onConfirm = { permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
        dismissText = "Запретить",
        onDismiss = { showAlertDialog = false },
    )
}

@Composable
fun RequestPermission(permission: String) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }
    var showAlertDialog by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasPermission = isGranted
    }

    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    showAlertDialog = !hasPermission

    /*val permissionText = when (permission) {
        Manifest.permission.ACCESS_COARSE_LOCATION -> "Разрешение на использование геолокации"
        Manifest.permission.ACCESS_FINE_LOCATION -> "Разрешение на использование геолокации"
        Manifest.permission.READ_EXTERNAL_STORAGE -> "Разрешение на чтение внешнего хранилища"
        else -> "Разрешение на использование $permission"
    }

    CustomAlertDialog(
        title = permissionText,
        confirmText = "Разрешить",
        onConfirm = { permissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION) },
        dismissText = "Запретить",
        onDismiss = { showAlertDialog = false },
    )*/
}
