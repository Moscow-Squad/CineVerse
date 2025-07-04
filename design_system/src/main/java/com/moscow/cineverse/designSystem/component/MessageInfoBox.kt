package com.moscow.cineverse.designSystem.component

import android.R.attr.width
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.example.design_system.R

@Composable
fun MessageInfoBox(
    title: String,
    description: String,
    firstButtonText: String,
    secondButtonText: String,
    onClickFirstButton: () -> Unit,
    onClickSecondButton: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = Theme.colors.brand.tertiary,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(width = 16.dp, height = 24.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Theme.colors.brand.primary.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.outline_arrow_left),
                    contentDescription = stringResource(R.string.arrow_left),
                    colorFilter = ColorFilter.tint(Theme.colors.brand.primary), // Apply tint
                    modifier = Modifier
                        .size(width = 24.dp, height = 24.dp)
                        .padding(end = 7.dp)
                )
            }
        }

        Text(
            text = title,
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = Modifier.padding(bottom = 8.dp, top = 16.dp)
        )
        Text(
            text = description,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.secondary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MovieButton(
                buttonText = firstButtonText,
                textColor = Theme.colors.button.onSecondary,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onClickFirstButton,
                buttonColor = Theme.colors.button.secondary,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.5.dp)
            )
            MovieButton(
                buttonText = secondButtonText,
                textColor = Theme.colors.button.onPrimary,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onClickSecondButton,
                buttonColor = Theme.colors.button.primary,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.5.dp)
            )
        }
    }}
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageInfoBoxExample() {
    CineVerseTheme {
        MessageInfoBox(
            title = "Alaa",
            description = "alaa",
            firstButtonText = "Button1",
            secondButtonText = "Button2",
            onClickFirstButton = {},
            onClickSecondButton = {},
            modifier = Modifier
                .padding(50.dp)
                .background(Color.Black)
        )
    }
}
