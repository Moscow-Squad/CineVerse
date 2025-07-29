package com.moscow.cineverse.designSystem.component

import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.image_viewer.component.SafeImageViewer
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.blur.OnBlurContent
import com.moscow.cineverse.designSystem.component.blur.RemoteImagePlaceholder

@Composable
fun SaveImage(
    modifier: Modifier = Modifier,
    posterUrl: String? = null
){
    SafeImageViewer(
        imageUrl = posterUrl ?: "",
        modifier = modifier,
        placeholderContent = {
            RemoteImagePlaceholder(
                Modifier
                    .fillMaxSize()
            )
        },
        errorContent = {
            RemoteImagePlaceholder(Modifier.fillMaxSize())
        },
    ) {
        OnBlurContent(
            hintText = stringResource(R.string.unsuitable_image),
            textStyle = TextStyle(
                fontSize = 8.sp,
                color = Color(0x99FFFFFF)
            ),
            iconSize = 16.dp,
            icon = painterResource(R.drawable.icon_eye_slash),
        )
    }
}