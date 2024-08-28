import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

@Composable
fun LightningIcon(modifier: Modifier = Modifier) {
    val lightningIcon = ImageVector.Builder(
        name = "Lightning",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = Brush.linearGradient(colors = listOf(Color.Gray, Color.Green, Color.Red)),
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
    }.build()

    Icon(
        painter = rememberVectorPainter(lightningIcon),
        contentDescription = "Lightning Icon",
        modifier = modifier.size(25.dp)
    )
}

@Composable
fun ActionButton(name: String, isEnabled: Boolean = true, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row {
            Text(text = name, style = TextStyle(fontSize = 22.sp))
            Spacer(modifier = Modifier.width(16.dp))
            LightningIcon(modifier = Modifier.size(25.dp))
        }
    }
}