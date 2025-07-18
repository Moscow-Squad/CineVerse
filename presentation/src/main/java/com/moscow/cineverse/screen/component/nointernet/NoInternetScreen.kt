package com.moscow.cineverse.screen.component.nointernet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.component.nointernet.NoInternetContent
import com.moscow.cineverse.designSystem.component.nointernet.NoInternetIcon
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
        NoInternetIcon(modifier = Modifier.padding(bottom = 16.dp))
        NoInternetContent(
            modifier = Modifier.padding(bottom = 24.dp),
            header = stringResource(R.string.oops_no_internet),
            content = stringResource(R.string.offline_reconnect)
        )
        MovieButton(
            modifier = Modifier.width(240.dp).height(40.dp),
            buttonText = stringResource(R.string.try_again),
            textColor = Theme.colors.button.onPrimary,
            buttonColor = Theme.colors.button.primary,
            textStyle = Theme.textStyle.body.small.medium.copy(
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            ),
            onClick = {onRetry()}
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