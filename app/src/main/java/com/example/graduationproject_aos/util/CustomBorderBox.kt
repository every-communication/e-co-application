package com.example.graduationproject_aos.util

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.unit.Dp

fun Modifier.customBorderBox(
    left: Dp,
    top: Dp,
    right: Dp,
    bottom: Dp,
    color: Color
): Modifier = this.then(
    Modifier.drawBehind {
        val paint = Paint().apply { this.color = color }
        // Draw top border
        drawRect(
            color = color,
            topLeft = Offset(0f, 0f),
            size = Size(size.width, top.toPx())
        )
        // Draw left border
        drawRect(
            color = color,
            topLeft = Offset(0f, 0f),
            size = Size(left.toPx(), size.height)
        )
        // Draw right border
        drawRect(
            color = color,
            topLeft = Offset(size.width - right.toPx(), 0f),
            size = Size(right.toPx(), size.height)
        )
        // Draw bottom border
        drawRect(
            color = color,
            topLeft = Offset(0f, size.height - bottom.toPx()),
            size = Size(size.width, bottom.toPx())
        )
    }
)
