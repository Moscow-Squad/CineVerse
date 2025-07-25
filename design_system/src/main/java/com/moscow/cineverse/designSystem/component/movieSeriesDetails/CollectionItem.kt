package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.component.MovieCircularProgressBar
import com.moscow.cineverse.designSystem.theme.CineVerseTheme

@Composable
fun CollectionItem(
    collectionName: String,
    showProgressBars: Boolean,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.large))
                .clickable(onClick = onItemClicked)
                .background(Theme.colors.background.bottomSheetCard)
                .padding(vertical = 16.dp, horizontal = 12.dp)
                ,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(R.drawable.due_tone_folder),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Theme.colors.brand.primary
        )

        Text(
            text = collectionName,
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.medium,
            modifier = Modifier.weight(1f)
        )

        if (showProgressBars) {
            MovieCircularProgressBar(
                modifier = Modifier.size(24.dp),
                gradientColors = listOf(
                    Theme.colors.brand.primary,
                    Theme.colors.brand.tertiary
                ),
                strokeWidth = 3.dp
            )
        }
    }

}

@Preview
@Composable
private fun CollectionItemPreview() {
    CineVerseTheme {
        CollectionItem(
            collectionName = "My Collection",
            showProgressBars = true,
            onItemClicked = {},
        )
    }
}