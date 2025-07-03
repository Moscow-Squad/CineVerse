package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MainDetails(
    profileImage: Int,
    name: String,
    date: String,
    location: String,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Theme.colors.background.card
        ),
        modifier = modifier.padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Image(
                    painter = painterResource(profileImage),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(64.dp)
                        .height(80.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 16.dp,
                                topEnd = 6.dp,
                                bottomStart = 6.dp,
                                bottomEnd = 16.dp
                            )
                        )
                )

                Column(modifier = Modifier.padding(start = 12.dp)) {
                    Text(
                        text = name,
                        color = Theme.colors.shade.primary,
                        style = Theme.textStyle.title.medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextWithIcon(
                        icon = R.drawable.outline_cake,
                        text = date,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    TextWithIcon(
                        icon = R.drawable.outline_location,
                        text = location
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                PillLabel(
                    text = "YouTube",
                    onClick = {},
                    isActive = true,
                    modifier = Modifier.weight(1f),
                    prefixIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.colored_youtube),
                            contentDescription = "YouTube Icon"
                        )
                    }
                )
                PillLabel(
                    text = "Facebook",
                    onClick = {},
                    isActive = true,
                    modifier = Modifier.weight(1f),
                    prefixIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.colored_facebook),
                            contentDescription = "Facebook Icon"
                        )
                    }
                )
                PillLabel(
                    text = "Instagram",
                    onClick = {},
                    isActive = true,
                    modifier = Modifier.weight(1f),
                    prefixIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.colored_instagram),
                            contentDescription = "Instagram Icon"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainDetailsPreview() {
    MainDetails(
        profileImage = R.drawable.profile_image,
        name = "Christian Bale",
        date = "Born on Jan 30, 1974",
        location = "In Cardiff, Wales, UK"
    )
}
