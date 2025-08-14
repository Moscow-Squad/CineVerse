package com.moscow.cineverse.screen.cast_detials.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.component.SectionTitle
import com.moscow.cineverse.screen.cast_detials.CastDetailsInteractionListener
import com.moscow.cineverse.screen.cast_detials.CastDetailsUiState
import com.moscow.cinverse.presentation.R

@Composable
fun ActorGallerySection(
    uiState: CastDetailsUiState,
    interactionListener: CastDetailsInteractionListener,
    modifier: Modifier = Modifier
) {
    if (uiState.images.isNotEmpty() && uiState.images.size >= 3) {
        Column(
            modifier = modifier
        ) {
            SectionTitle(
                title = stringResource(R.string.gallery),
                onClick = {
                    interactionListener.onShowMoreGallery()
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            GallerySection(
                images = uiState.images.take(3),
                enableBlur = uiState.enableBlur,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}
