package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.layout.Arrangement
import com.example.design_system.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme

@Composable
fun MovieRatingBar(
    rating: Int, onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        for (i in 1..5) {
            IconButton(modifier = Modifier.size(16.dp),
                onClick = { onRatingChanged(i.toFloat()) }) {
                Icon(
                    if (i <= rating) painterResource(R.drawable.yellow_star) else painterResource(R.drawable.outline_star),
                    contentDescription = stringResource(R.string.star_icon),
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
        }
    }


}

@Preview
@Composable
private fun PreviewRatingBar() {
    CineVerseTheme {
        MovieRatingBar(3, {})
    }
}