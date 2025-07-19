package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun EmptyState(
    icon: Painter,
    title:String,
    description:String,
    modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .width(240.dp)
            .background(Theme.colors.background.screen),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            painter = icon,
            tint = Theme.colors.brand.primary,
            contentDescription = "",
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 16.dp)
        )

        Text(

            title,
            modifier = Modifier.padding(bottom = 8.dp),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )

        Text(
            description,
            textAlign = TextAlign.Center,
            style = Theme.textStyle.body.small.medium,
            color = Theme.colors.shade.secondary
        )



    }
}

@Preview
@Composable
private fun PreviewEmptyState() {

    EmptyState(
        icon = painterResource(Theme.icons.dueTone.search),
        title = "Nothing Found",
        description = "We searched the entire universe… but found nothing matching your query. Try something else?"
    )
}