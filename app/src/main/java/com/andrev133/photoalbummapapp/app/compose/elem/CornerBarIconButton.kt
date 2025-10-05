package com.andrev133.photoalbummapapp.app.compose.elem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

enum class CornerBarButtonArrangement {
    START,
    END
}

@Composable
fun CornerBarIconButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    arrangement: CornerBarButtonArrangement,
    iconDescription: String? = null,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .backgroundShapeBySide(
                arrangement = arrangement,
                color = MaterialTheme.colorScheme.primary
            ),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.padding(8.dp),
            painter = icon,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = iconDescription
        )
    }
}

@Composable
private fun Modifier.backgroundShapeBySide(
    arrangement: CornerBarButtonArrangement,
    color: Color
) = when (arrangement) {
    CornerBarButtonArrangement.START -> this
        .background(
            shape = RoundedCornerShape(
                topStart = 0.dp,
                bottomStart = 0.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp
            ),
            color = color
        )

    CornerBarButtonArrangement.END -> this
        .background(
            shape = RoundedCornerShape(
                topStart = 16.dp,
                bottomStart = 16.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp
            ),
            color = color
        )
}