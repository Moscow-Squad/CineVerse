package com.moscow.cineverse.designSystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.design_system.R
import com.example.image_viewer.component.SafeImageViewer

@Composable
fun SaveImage(
    modifier: Modifier = Modifier,
    posterUrl: String? = null,
    contentDescription: String? = null
){
    SafeImageViewer(
        model = posterUrl,
        contentDescription = contentDescription,
        fallback = painterResource(R.drawable.due_tone_image),
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}