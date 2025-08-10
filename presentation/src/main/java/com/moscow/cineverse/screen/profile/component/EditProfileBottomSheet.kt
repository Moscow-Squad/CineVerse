package com.moscow.cineverse.screen.profile.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.component.card.MessageInfoCard
import com.moscow.cinverse.presentation.R

@Composable
fun EditProfileBottomSheet(
    visible: Boolean,
    isGuest: Boolean,
    onDismiss: () -> Unit,
    onEditProfile: () -> Unit,
    onLoginClick: () -> Unit
) {
    AnimatedVisibility(visible) {
        CineVerseBottomSheet(
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            showCancelIcon = true,
        ) {
            MessageInfoCard(
                title = if (isGuest) stringResource(R.string.you_re_almost_there) else stringResource(
                    R.string.edit_profile
                ),
                description = if (isGuest) stringResource(R.string.log_in_to_update_your_name_profile_picture_and_account_details) else stringResource(
                    R.string.you_ll_be_taken_to_the_website_to_update_your_name_username_or_profile_picture
                ),
                icon = if (isGuest) painterResource(R.drawable.due_tone_login) else painterResource(
                    R.drawable.due_tone_link_minimalistic
                ),
                showButtonsGroup = true,
                firstButtonText = if (isGuest) stringResource(R.string.not_now) else stringResource(
                    R.string.cancel
                ),
                onClickFirstButton = onDismiss,
                secondButtonText = if (isGuest) stringResource(R.string.login) else stringResource(
                    R.string.go_to_website
                ),
                onClickSecondButton = if (isGuest) onLoginClick else onEditProfile,
                modifier = Modifier.padding(bottom = 16.dp),
            )
        }
    }
}
