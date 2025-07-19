package com.moscow.cineverse.designSystem.component.nointernet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun NoInternetIcon(modifier: Modifier = Modifier){
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(shape = CircleShape, color = Theme.colors.additional.secondary.red)
    ){
        Icon(
            painter = painterResource(Theme.icons.dueTone.station),
            contentDescription = "no internet connection",
            tint = Theme.colors.additional.primary.red,
            modifier = Modifier.size(20.dp).align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun NoInternetIconPreview(){
    CineVerseTheme {
        NoInternetIcon()
    }
}