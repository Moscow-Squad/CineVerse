package com.moscow.cineverse.designSystem.component

import com.example.design_system.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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

@Composable
fun MainDetails(
    name: String,
    date: String,
    location: String,
    modifier: Modifier=Modifier
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .background(Theme.colors.background.card)
    )
    {
        Row {
            Image(
                painter = painterResource(id = R.drawable.outline_user),
                contentDescription = "Logo",
                modifier = Modifier
                    .width(64.dp)
                    .height(80.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 6.dp,
                            topEnd = 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 6.dp
                        )
                    )
            )

            Column {
                Text(
                    text = name,
                    color = Theme.colors.shade.primary,
                    style = Theme.textStyle.title.medium
                )
                TextWithIcon(
                    icon = R.drawable.outline_cake,
                    text = date)
                TextWithIcon(
                    icon = R.drawable.outline_location,
                    text = location)
            }
        }

        PillLabel(text = "YouTube", onClick = {}, isActive = true,
            modifier = Modifier.padding(top = 12.dp, end = 8.dp),
            prefixIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.colored_youtube),
                    contentDescription = "YouTube Icon"
                )
            }
        )
        PillLabel(text = "Facebook", onClick = {}, isActive = true,
            modifier = Modifier.padding(top = 12.dp, end = 8.dp),
            prefixIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.colored_facebook),
                    contentDescription = "Facebook Icon"
                )
            }
        )
        PillLabel(text = "Instagram", onClick = {}, isActive = true,
            modifier = Modifier.padding(top = 12.dp, end = 8.dp),
            prefixIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.colored_instagram),
                    contentDescription = "Instagram Icon"
                )
            }
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MainDetailsPreview() {
    MainDetails(name = "Christian Bale", date = "Born on Jan 30, 1974", location = "In Cardiff, Wales, UK")
}