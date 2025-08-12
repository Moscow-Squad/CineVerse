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
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun LogoutBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    AnimatedVisibility(visible) {
        CineVerseBottomSheet(
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            showCancelIcon = true,
        ) {
            MessageInfoCard(
                title = stringResource(R.string.are_you_sure_you_want_to_logout),
                description = stringResource(R.string.you_can_always_sign_back_in_with_your_account),
                icon = painterResource(R.drawable.due_tone_logout),
                showButtonsGroup = true,
                firstButtonText = stringResource(R.string.cancel),
                onClickFirstButton = onDismiss,
                secondButtonText = stringResource(R.string.logout),
                onClickSecondButton = onLogoutClick,
                modifier = Modifier.padding(bottom = 16.dp),
                iconColor = Theme.colors.additional.primary.red,
                secondButtonBackground = Theme.colors.additional.primary.red,
                iconCircleBackgroundColor = Theme.colors.additional.secondary.red
            )
        }
    }
}
