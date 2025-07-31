package com.moscow.cineverse.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
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
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
) {
    Column(
        modifier = modifier.wrapContentWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .size(64.dp)
                .clip(CircleShape)
                .background(shape = CircleShape, color = Theme.colors.additional.secondary.red)
        ) {
            Icon(
                painter = painterResource(Theme.icons.dueTone.station),
                contentDescription = "no internet connection",
                tint = Theme.colors.additional.primary.red,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.Center)
            )
        }
        Column(
            modifier = modifier.width(240.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.oops_no_internet),
                style = Theme.textStyle.title.medium,
                color = Theme.colors.shade.primary,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.offline_reconnect),
                style = Theme.textStyle.body.small.medium,
                color = Theme.colors.shade.secondary,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
        MovieButton(
            modifier = Modifier
                .padding(top = 24.dp)
                .width(240.dp)
                .height(40.dp),
            buttonText = stringResource(R.string.try_again),
            textColor = Theme.colors.button.onPrimary,
            buttonColor = Theme.colors.button.primary,
            textStyle = Theme.textStyle.body.small.medium.copy(
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            ),
            onClick = { onRetry() }
        )
    }
}

@Preview
@Composable
private fun NoInternetScreenPreview() {
    CineVerseTheme {
        NoInternetScreen()
    }
}