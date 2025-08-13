package com.moscow.cineverse.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

@Composable
fun SwipeToDelete(
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    swipeThreshold: Dp = 80.dp,
    animationDurationMs: Int = 200,
    deleteDelayMs: Long = 300,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val coroutineScope = rememberCoroutineScope()
    val isDarkTheme = isSystemInDarkTheme()
    val layoutDirection = LocalLayoutDirection.current
    val isRtl = layoutDirection == LayoutDirection.Rtl
    val maxSwipeDistance = with(density) { swipeThreshold.toPx() }
    val offsetX = remember { Animatable(0f) }
    var showDeleteAnimation by remember { mutableStateOf(false) }
//
//    val handleDelete = {
//        coroutineScope.launch {
//            delay(deleteDelayMs)
//            onDelete()
//            showDeleteAnimation = true
//        }
//    }
//
//    AnimatedVisibility(
//        visible = isVisible && !showDeleteAnimation,
//        exit = shrinkVertically(
//            animationSpec = tween(300),
//            shrinkTowards = Alignment.CenterVertically
//        ) + fadeOut(tween(200))
//    ) {
//
//    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Always use negative offsetX for revealing
            val revealedWidth = (-offsetX.value).coerceAtLeast(0f)

            if (revealedWidth > 0f) {
                val revealedWidthDp = with(density) { revealedWidth.toDp() }

                Box(
                    modifier = Modifier
                        .width(revealedWidthDp)
                        .height(96.dp)
                        .align(Alignment.CenterEnd), // Always align to end
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .let { modifier ->
                                if (isDarkTheme) {
                                    modifier.shadow(
                                        elevation = 8.dp,
                                        shape = RoundedCornerShape(Theme.radius.large),
                                        ambientColor = Theme.colors.additional.primary.red.copy(
                                            alpha = 0.4f
                                        ),
                                        spotColor = Theme.colors.additional.primary.red.copy(
                                            alpha = 0.6f
                                        )
                                    )
                                } else {
                                    modifier
                                }
                            }
                            .clip(RoundedCornerShape(Theme.radius.large))
                            .background(Theme.colors.additional.primary.red)
                            .clickable { onDelete() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.due_tone_trash),
                            contentDescription = "Delete",
                            tint = Theme.colors.button.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                val threshold = -maxSwipeDistance / 2

                                if (offsetX.value <= threshold) {
                                    offsetX.animateTo(
                                        targetValue = -maxSwipeDistance,
                                        animationSpec = tween(animationDurationMs)
                                    )
                                } else {
                                    offsetX.animateTo(
                                        targetValue = 0f,
                                        animationSpec = tween(animationDurationMs)
                                    )
                                }
                            }
                        }
                    ) { _, dragAmount ->
                        coroutineScope.launch {
                            // Invert drag amount in RTL mode
                            val adjustedDragAmount = if (isRtl) -dragAmount else dragAmount

                            val newOffset = (offsetX.value + adjustedDragAmount)
                                .coerceIn(-maxSwipeDistance, 0f)

                            offsetX.snapTo(newOffset)
                        }
                    }
                }
        ) {
            content()
        }
    }
}