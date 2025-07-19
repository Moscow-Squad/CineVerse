package com.moscow.cineverse.designSystem.component.nointernet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun NoInternetContent(
    header: String,
    content: String,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.width(240.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = header,
            style = Theme.textStyle.title.medium,
            color = Theme.colors.shade.primary,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = content,
            style = Theme.textStyle.body.small.medium,
            color = Theme.colors.shade.secondary,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
private fun NoInternetContentPreview(){
    CineVerseTheme {
        NoInternetContent(
            header = "Oops! No Internet",
            content = "Looks like you're offline. Let's reconnect so you don't miss out!"
        )
    }
}