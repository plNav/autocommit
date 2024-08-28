package com.pavdev.autocommit.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pavdev.autocommit.ui.theme.iconColor

@Composable
fun AutocommitIcon(
    size: Dp = 32.dp,
    color: Color = iconColor,
    backgroundColor: Color = Color.Transparent
) {
    val brush = Brush.horizontalGradient(listOf(Color.White, Color.White))
    val gitHubLightningIcon = ImageVector.Builder(
        name = "GitHubLightning",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
        tintColor = Color.Red,
    ).apply {
        // Circle path
        path(
            stroke = brush,
            strokeLineWidth = 2.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12f, 1f)
            arcToRelative(
                a = 11f,
                b = 11f,
                theta = 0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0f,
                dy1 = 22f
            )
            arcToRelative(
                a = 11f,
                b = 11f,
                theta = 0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                dx1 = 0f,
                dy1 = -22f
            )
            close()
        }
        // Lightning bolt path
        path(
            stroke = brush,
            // fill = brush,
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(13f, 3f)
            lineTo(13f, 10f)
            lineTo(19f, 10f)
            lineTo(11f, 21f)
            lineTo(11f, 14f)
            lineTo(5f, 14f)
            lineTo(13f, 3f)
            close()
        }
        // Triangle path at the top left of the circle
        path(
            fill = brush,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(2f, 1f)
            lineTo(8f, 2f)
            lineTo(3f, 6f)
            close()
        }
        // Triangle path at the top right of the circle
        path(
            fill = brush,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(22f, 1f)
            lineTo(16f, 2f)
            lineTo(21f, 6f)
            close()
        }
    }.build()

    Icon(
        painter = rememberVectorPainter(gitHubLightningIcon),
        contentDescription = "Autocommit Icon",
        tint = color,
        modifier = Modifier
            .size(size)
            .background(color = backgroundColor)
            .padding(6.dp)
    )
}