package com.moscow.cineverse.designSystem.component.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.CTACard
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun AdvertisementSection(
    sectionTitle: String,
    advertisementTitle: String,
    advertisementCaption: String,
    onClickArrow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = sectionTitle,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary
        )
        CTACard(
            icon = painterResource(R.drawable.due_tone_magic_stick),
            title = advertisementTitle,
            caption = advertisementCaption,
            onClickArrow = onClickArrow  // ToDo: Will add Action Later
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AdvertisementSectionPreview() {
    AdvertisementSection(
        advertisementTitle = "What Should I Watch?",
        sectionTitle = "What Should I Watch?",
        advertisementCaption = "Explore all movies and series by genre or search for anything you like.",
        onClickArrow = {}
    )
}