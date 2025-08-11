package com.moscow.cineverse.component


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun NoHistoryScreen (
    modifier: Modifier = Modifier,
    onContinue: () -> Unit = {}

) {
    Column(
        modifier = modifier.wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.due_tone_history),
            contentDescription = "no history yet",
            tint = Theme.colors.brand.primary,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(64.dp)
                .clip(CircleShape)
                .background(Theme.colors.button.disabled)
                .padding(18.dp)

        )
        Column (
            modifier = Modifier.width(240.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.no_history_yet),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.shade.primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.history_empty_message),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

        }
        MovieButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .width(240.dp),
            buttonText = stringResource(R.string.find_something_to_watch),
            textColor = Theme.colors.button.onPrimary,
            buttonColor = Theme.colors.button.primary,
            textStyle = Theme.textStyle.body.small.medium.copy(textAlign = TextAlign.Center,

            ),
            onClick = { onContinue()}
        )
    }
}
@Preview
@Composable
private fun NoHistoryScreenPreview() {
    CineVerseTheme {
        NoHistoryScreen(
            onContinue = {},
        )
    }
}

