package com.andrev133.photoalbummapapp.app.compose.elem

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay

/*

@Composable
fun RequestPermission(permission: String) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(false) }
//    var showAlertDialog by remember { mutableStateOf(false) }

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

        if (!hasPermission) {
            permissionLauncher.launch(permission)
        }
    }
}

*/

@Composable
fun RequestCoarseLocationPermission() {
    val context = LocalContext.current

    // Для Android ниже 6.0 (API 23) разрешение не требуется
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            delay(500)

            // На Android 10+ лучше запрашивать FINE_LOCATION
            val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Manifest.permission.ACCESS_FINE_LOCATION
            } else {
                Manifest.permission.ACCESS_COARSE_LOCATION
            }

            permissionLauncher.launch(permissionToRequest)
        }
    }
}

@Composable
fun RequestReadStoragePermission() {
    val context = LocalContext.current

    // Для Android ниже 6.0 (API 23) разрешение не требуется
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        return
    }

    val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // Android 13+ - новые разрешения
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            // Добавь READ_MEDIA_AUDIO если нужно
        )
    } else {
        // Android 12 и ниже
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    LaunchedEffect(Unit) {
        val hasAllPermissions = permissionsToRequest.all { permission ->
            ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }

        if (!hasAllPermissions) {
            delay(500)
            permissionLauncher.launch(permissionsToRequest)
        }
    }
}

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