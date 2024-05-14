import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp
import com.saeed.zanjan.receipt.ui.theme.CustomColors

@Composable
fun HorizontalDashedLine() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val startY = size.height / 2
            val startX = 0f
            val endX = size.width
            val strokeWidth = 2.dp.toPx()
            val dashWidth = 8.dp.toPx()
            val dashGap = 4.dp.toPx()

            drawLine(
                color = CustomColors.lightGray,
                start = Offset(startX, startY),
                end = Offset(endX, startY),
                strokeWidth = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashWidth, dashGap))
            )
        }
    }
}