package com.moscow.cineverse.screen.movieSeriesDetails

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun MovieRatingBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onRatingSubmit: (Int) -> Unit,
    onRatingRemove: () -> Unit = {},
    initialRating: Int = 0,
    hasExistingRating: Boolean = false,
    isLoading: Boolean = false
) {
    var selectedRating by remember { mutableStateOf(initialRating) }
    val isEditMode = hasExistingRating && initialRating > 0
    val currentButtonText = if (isEditMode) stringResource(R.string.change_rating)
    else stringResource(R.string.add_rating)

    if (isVisible) {
        CineVerseBottomSheet(
            title = stringResource(R.string.rate_the_movie),
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmojiAndStarRating(
                    selectedRating = selectedRating,
                    onRatingChanged = { selectedRating = it },
                    emojiDrawables = listOf(
                        R.drawable.colored_frowning_face,
                        R.drawable.colored_confused_face,
                        R.drawable.colored_neutral_face,
                        R.drawable.colored_smiling_face_with_smiling_eyes,
                        R.drawable.colored_star_struck,
                    )
                )

                Spacer(modifier = Modifier.size(24.dp))

                MovieButton(
                    buttonText = currentButtonText,
                    textColor = Theme.colors.button.onPrimary,
                    textStyle = Theme.textStyle.label.medium.medium,
                    buttonColor = Theme.colors.button.primary,
                    onClick = {
                        if (selectedRating > 0) {
                            onRatingSubmit(selectedRating)
                        }
                    },
                    enable = selectedRating > 0 && !isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                )

                if (isEditMode) {
                    Spacer(modifier = Modifier.size(8.dp))

                    MovieButton(
                        buttonText = stringResource(R.string.remove_rating),
                        textColor = Theme.colors.button.onTertiary,
                        textStyle = Theme.textStyle.body.medium.medium,
                        buttonColor = Color.Transparent,
                        onClick = {
                            selectedRating = 0
                            onRatingRemove()
                        },
                        enable = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)

                    )
                }
            }
        }
    }
}

@Composable
private fun EmojiAndStarRating(
    selectedRating: Int,
    onRatingChanged: (Int) -> Unit,
    emojiDrawables: List<Int>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            emojiDrawables.forEachIndexed { index, emojiDrawable ->
                val rating = index + 1
                val isSelected = selectedRating == rating

                val animatedScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.5f else 1f,
                    animationSpec = tween(durationMillis = 300),
                    label = "emoji_scale_animation"
                )

                val animatedAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.6f,
                    animationSpec = tween(durationMillis = 300),
                    label = "emoji_alpha_animation"
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onRatingChanged(rating) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(emojiDrawable),
                        contentDescription = "Rating emoji $rating",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(if (isSelected) 24.dp else 16.dp)
                            .scale(animatedScale)
                            .alpha(animatedAlpha)
                    )
                }

                if (index < emojiDrawables.size - 1) {
                    Spacer(modifier = Modifier.size(12.dp))
                }
            }
        }
        MovieRatingBar(
            rating = selectedRating,
            onRatingChanged = { onRatingChanged(it.toInt()) },
            starSize = 24.dp,
            spacing = 12.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieRatingBottomSheetPreview() {
    CineVerseTheme {
        var showBottomSheet by remember { mutableStateOf(true) }
        var isLoading by remember { mutableStateOf(false) }
        var hasExistingRating by remember { mutableStateOf(false) }
        var currentRating by remember { mutableStateOf(0) }

        MovieRatingBottomSheet(
            isVisible = showBottomSheet,
            onDismiss = { showBottomSheet = false },
            onRatingSubmit = { rating ->
                isLoading = true
                println("Rating submitted: $rating")
                currentRating = rating
                hasExistingRating = true
                isLoading = false
            },
            onRatingRemove = {
                println("Rating removed")
                currentRating = 0
                hasExistingRating = false
                showBottomSheet = false
            },
            isLoading = isLoading,
            initialRating = currentRating,
            hasExistingRating = hasExistingRating
        )
    }
}