package com.moscow.cineverse.designSystem.component.indicator

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun MovieCircularProgressBar(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 5.dp,
    gradientColors: List<Color> = listOf(Theme.colors.brand.primary, Theme.colors.brand.tertiary),
) {
    val rotation by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = -360f,
        animationSpec = infiniteRepeatable(
            tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(
        modifier = modifier.size(size = 40.dp).rotate(degrees = rotation)
    ) {
        val center = center
        val radius = size.minDimension / 2
        val strokeWidthPx = strokeWidth.toPx()

        drawArc(
            brush = Brush.sweepGradient(
                colors = gradientColors,
                center = center
            ),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(
                width = strokeWidthPx,
                cap = StrokeCap.Butt
            )
        )

        val angleRad = Math.toRadians(0.0)
        val dotRadius = strokeWidthPx / 2

        val dotX = center.x + radius * cos(angleRad).toFloat()
        val dotY = center.y + radius * sin(angleRad).toFloat()


        drawCircle(
            color = gradientColors[0],
            radius = dotRadius,
            center =  Offset(dotX, dotY)
        )

    }
}