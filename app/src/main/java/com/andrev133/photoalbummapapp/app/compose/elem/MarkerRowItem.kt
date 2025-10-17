package com.andrev133.photoalbummapapp.app.compose.elem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.andrev133.photoalbummapapp.R
import com.andrev133.photoalbummapapp.app.ui.theme.AutoMediaAppTheme
import com.andrev133.photoalbummapapp.app.ui.theme.endRoundedRect
import com.andrev133.photoalbummapapp.app.ui.theme.startRoundedRect
import com.andrev133.photoalbummapapp.domain.model.PhotoCollectionMarkerModel

@Composable
fun MarkerRowItem(
    modifier: Modifier = Modifier,
    model: PhotoCollectionMarkerModel,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .shadow(elevation = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = Color.Black,
                    shape = MaterialTheme.shapes.startRoundedRect
                )
                .padding(top = 8.dp, bottom = 8.dp, start = 8.dp),
            painter = painterResource(R.drawable.ic_marker),
            tint = model.color,
            contentDescription = model.title
        )

        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
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
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
                text = model.title
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MarkerRowItemPreview() {
    AutoMediaAppTheme {
        MarkerRowItem(
            modifier = Modifier.size(600.dp, 200.dp),
            model = PhotoCollectionMarkerModel(
                title = "Marker",
                color = MaterialTheme.colorScheme.primary,
            ),
            onClick = {}
        )
    }
}