package com.moscow.cineverse.details.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun StaffInfoItem(
    role: String,
    name: String,
    modifier: Modifier = Modifier,
    isDividerVisible: Boolean = true
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = role,
                style = Theme.textStyle.body.medium.regular,
                color = Theme.colors.shade.secondary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                style = Theme.textStyle.body.medium.medium,
                color = Theme.colors.shade.primary,
                modifier = Modifier.weight(1f)
            )
        }
        if (isDividerVisible){
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Theme.colors.stroke.primary)
            )
        }
    }
}

@Preview
@Composable
private fun StaffInfoItemPreview() {
    CineVerseTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.card)
                .padding(10.dp)
        ) {
            StaffInfoItem(
                role = "Director",
                name = "John Doe",
            )
        }
    }

}