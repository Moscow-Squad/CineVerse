package com.moscow.cineverse.details.series_details.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.card.MessageInfoCard
import com.moscow.cinverse.presentation.R

@Composable
fun LoginBottomSheet(
    isVisible: Boolean,
    onDismissLoginBottomSheet: () -> Unit,
    navigateToLogin: () -> Unit
) {
    if (isVisible) {
        CineVerseBottomSheet(
            title = stringResource(R.string.you_re_almost_there),
            onClose = { onDismissLoginBottomSheet() },
            onDismissRequest = { onDismissLoginBottomSheet() },
            showCancelIcon = true,
            onAddNewCollectionClick = {}
        ) {
            MessageInfoCard(
                title = stringResource(R.string.you_re_almost_there),
                description = stringResource(R.string.log_in_to_save_movies_create_collections_and_get_personalized_recommendations),
                icon = painterResource(R.drawable.due_tone_video_library),
                showButtonsGroup = true,
                firstButtonText = stringResource(R.string.not_now),
                onClickFirstButton = { onDismissLoginBottomSheet() },
                secondButtonText = stringResource(R.string.log_in),
                onClickSecondButton = { navigateToLogin() },
            )
        }
    }
}