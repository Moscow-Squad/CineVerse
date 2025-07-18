package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun StaffInfoSection(
    modifier: Modifier = Modifier,
    staffInfo: List<Pair<String, String>> // (role, name)
) {

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = stringResource(R.string.behind_the_scenes),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary,
            )

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.large))
                .background(Theme.colors.background.card)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            staffInfo.forEachIndexed { index, item ->
                StaffInfoItem(
                    role = item.first,
                    name = item.second,
                    isDividerVisible = index != staffInfo.lastIndex
                )
            }
        }
    }
}

@Preview
@Composable
private fun StaffInfoSectionPreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(10.dp)
        ) {
            StaffInfoSection(
                staffInfo = listOf(
                    "Director" to "John Doe",
                    "Director, Screenplay, Story" to "Christopher Nolan",
                    "Producer" to "Jane Smith",
                    "Writer" to "Alice Johnson"
                )
            )
        }
    }
}