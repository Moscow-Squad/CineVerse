package com.moscow.cineverse.details.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.common_ui_state.CastUiState
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.image_viewer.component.SafeImageViewer

@Composable
fun CastCard(
    enableBlur: String,
    modifier: Modifier = Modifier,
    castMember: CastUiState,
    onClick: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .width(200.dp)
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(Theme.colors.background.card)
            .clickable { onClick() }
            .then(modifier)
    ) {
        SafeImageViewer(
            imageUrl = castMember.profileImage,
            modifier = Modifier
                .size(64.dp)
                .clip(
                    RoundedCornerShape(
                        topEnd = Theme.radius.large,
                        topStart = Theme.radius.large,
                        bottomStart = Theme.radius.large
                    )
                ),
            isBlurEnabled = enableBlur,
            placeholderContent = { RemoteImagePlaceholder() },
            errorContent = { RemoteImagePlaceholder() },
            onBlurContent = { OnBlurContent(isAddedText = false) }
        )
        CastMemberNames(
            originalName = castMember.originalName,
            characterName = castMember.characterName
        )

    }
}

@Composable
private fun CastMemberNames(
    originalName: String,
    characterName: String
) {
    Column(
        modifier = Modifier.padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = originalName,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.primary,
            maxLines = 1
        )

        Text(
            text = characterName,
            style = Theme.textStyle.body.small.regular,
            color = Theme.colors.shade.secondary,
            maxLines = 1
        )
    }
}
