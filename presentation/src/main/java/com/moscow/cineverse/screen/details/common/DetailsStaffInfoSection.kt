package com.moscow.cineverse.screen.details.common

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cinverse.presentation.R

@Composable
fun DetailsStaffInfoSection(
    characters: List<String>,
    director: List<String>,
    produce: List<String>,
    writer: List<String>,
) {
    StaffInfoSection(
        staffInfo = listOf(
            stringResource(R.string.characters) to characters.joinToString(","),
            stringResource(R.string.director_screenplay_story) to director.joinToString(","),
            stringResource(R.string.producer) to produce.joinToString(","),
            stringResource(R.string.writer) to writer.joinToString(",")
        ),
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
    )
}