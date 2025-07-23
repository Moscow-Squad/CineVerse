package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SaveImage(
    modifier: Modifier = Modifier,
    posterUrl: String? = null,
    contentDescription: String? = null
){
    SafeImageViewer(
        imageUrl = posterUrl!!,
        //fallback = painterResource(R.drawable.due_tone_image),
        modifier = modifier,
        //contentScale = ContentScale.Crop,
        placeholderContent = {
            RemoteImagePlaceholder(Modifier.fillMaxSize())
        },
        errorContent = {
            RemoteImagePlaceholder(Modifier.fillMaxSize())
        },
    ) {
        OnBlurContent(
            hintText = stringResource(R.string.unsuitable_image),
            textStyle = Theme.textStyle.body.small.regular.copy(
                color = Color(0x99FFFFFF)
            ),
            iconSize = 24.dp,
            icon = painterResource(R.drawable.icon_eye_slash),
        )
    }
}