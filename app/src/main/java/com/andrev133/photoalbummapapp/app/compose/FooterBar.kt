package com.andrev133.photoalbummapapp.app.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.app.compose.elem.CornerBarButtonArrangement
import com.andrev133.photoalbummapapp.app.compose.elem.CornerBarIconButton
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme

@Composable
fun FooterBar(
    modifier: Modifier = Modifier,
    onMarkerListClick: () -> Unit,
    onCentralClick: () -> Unit,
    onTravelClick: () -> Unit
) {
    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
            .onSizeChanged { size = it },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val cornerButtonModifier = Modifier
            .fillMaxHeight()
            .width(88.dp)
            .padding(vertical = 8.dp)

        CornerBarIconButton(
            modifier = cornerButtonModifier,
            icon = painterResource(id = R.drawable.ic_labels),
            arrangement = CornerBarButtonArrangement.START,
            onClick = onMarkerListClick
        )

        IconButton(
            modifier = Modifier
                .size(size.height.dp * 0.3f)
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(
                        corner = CornerSize(30.dp)
                    )
                )
                .padding(8.dp),
            onClick = onCentralClick
        ) {
            Icon(
                modifier = Modifier
                    .padding(4.dp),
                painter = painterResource(id = R.drawable.ic_marker),
                tint = MaterialTheme.colorScheme.onTertiary,
                contentDescription = null
            )
        }

        CornerBarIconButton(
            modifier = cornerButtonModifier,
            icon = painterResource(id = R.drawable.ic_travel),
            arrangement = CornerBarButtonArrangement.END,
            onClick = onTravelClick
        )
    }
}

@Preview
@Composable
fun FooterBarPreview() {
    AutoMediaAppTheme {
        FooterBar(
            modifier = Modifier
                .width(400.dp)
                .height(200.dp),
            onMarkerListClick = {},
            onCentralClick = {},
            onTravelClick = {}
        )
    }
}