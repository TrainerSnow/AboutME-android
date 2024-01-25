package com.aboutme.feature.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aboutme.core.model.daily.DailyDataProgress
import com.aboutme.feature.home.R
import kotlinx.coroutines.async
import kotlin.math.roundToInt
import androidx.compose.animation.Animatable as ColorAnimatable

private fun <T> animSpec() = tween<T>(durationMillis = 1000)

@Suppress("DeferredResultUnused")
@Composable
internal fun ProgressCard(
    modifier: Modifier = Modifier,
    progress: DailyDataProgress
) {
    val initialColor = MaterialTheme.colorScheme.surfaceVariant
    val color = remember { ColorAnimatable(initialColor) }

    val percentage = remember { Animatable(0F) }

    LaunchedEffect(progress.completed) {
        async {
            color.animateTo(
                ColorGradient.getForPercent(progress.getPercentage()),
                animationSpec = animSpec()
            )
        }
        async {
            percentage.animateTo(
                progress.getPercentage(),
                animSpec()
            )
        }
    }

    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.progress_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = stringResource(
                R.string.progress_numeric,
                progress.completed, progress.total
            ),
            style = MaterialTheme.typography.titleSmall.copy(color = color.value)
        )
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 4.dp,
                    vertical = 12.dp
                ),
            color = color.value,
            percent = percentage.value
        )
    }
}

@Composable
private fun ProgressBar(
    modifier: Modifier = Modifier,
    color: Color,
    percent: Float
) {
    val pathColor = MaterialTheme.colorScheme.surfaceVariant
    val strokeWidth = 40F

    Canvas(modifier) {
        drawLine(
            color = pathColor,
            start = Offset(x = 0F, y = size.height / 2F),
            end = Offset(x = size.width, y = size.height / 2F),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(x = 0F, y = size.height / 2F),
            end = Offset(x = size.width * percent, y = size.height / 2F),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

private val ColorGradient = object {

    private val colors = listOf(
        Color(0xFFB12B1D),
        Color(0xFFCE4724),
        Color(0xFFB1671E),
        Color(0xFFDD9044),
        Color(0xFFB19719),
        Color(0xFF98A213),
        Color(0xFF7BB11B),
        Color(0xFF61F186)
    )

    fun getForPercent(percent: Float): Color {
        require(percent in 0F..1F)
        return colors[(percent * (colors.size - 1)).roundToInt()]
    }

}

@Preview
@Composable
private fun ProgressCardPreview() {
    Surface {
        ProgressCard(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp),
            progress = DailyDataProgress(1, 8)
        )
    }
}