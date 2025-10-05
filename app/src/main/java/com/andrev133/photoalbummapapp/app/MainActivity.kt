package com.andrev133.photoalbummapapp.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.BuildConfig
import com.andrev133.photoalbummapapp.app.compose.FooterBar
import com.andrev133.photoalbummapapp.app.compose.MapView
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme
import ru.sulgik.mapkit.MapKit
import ru.sulgik.mapkit.compose.bindToLifecycleOwner
import ru.sulgik.mapkit.compose.rememberAndInitializeMapKit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AutoMediaAppTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    MapKit.setApiKey(BuildConfig.MAP_API_KEY)
    MapKit.initialize(LocalContext.current)

    rememberAndInitializeMapKit().bindToLifecycleOwner()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        content = { innerPadding ->
            MapView(
                modifier = Modifier
                    .padding(innerPadding)
            )
        },
        bottomBar = {
            FooterBar(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    AutoMediaAppTheme {
        MainScreen()
    }
}