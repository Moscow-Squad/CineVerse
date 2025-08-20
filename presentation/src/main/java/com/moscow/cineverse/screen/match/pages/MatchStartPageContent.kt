package com.moscow.cineverse.screen.match.pages

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.navigation.LocalScaffoldPaddingValues
import com.moscow.cinverse.presentation.R as PresentationR


@Composable
fun MatchStartPageContent(
    modifier: Modifier = Modifier,
    onClickStartMatching: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Theme.colors.background.screen)
            .padding(bottom = LocalScaffoldPaddingValues.current.calculateBottomPadding())
    ) {
        MovieAppBar(
            modifier = Modifier.fillMaxWidth(),
            textPaddings = PaddingValues(horizontal = 8.dp),
            title = stringResource(PresentationR.string.discover_your_match),
            showBackButton = false,
            showAddButton = false,
            showLogo = false,
            showDivider = true
        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(horizontal = 60.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Theme.colors.brand.tertiary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(PresentationR.drawable.magic_stick_icon),
                    modifier = Modifier.size(28.dp),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(PresentationR.string.not_sure_watch),
                style = Theme.textStyle.title.small,
                color = Theme.colors.shade.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(PresentationR.string.answer_questions),
                style = Theme.textStyle.body.small.medium.copy(
                    textAlign = TextAlign.Center
                ),
                color = Theme.colors.shade.secondary
            )

            Spacer(modifier = Modifier.height(24.dp))

            MovieButton(
                buttonText = stringResource(PresentationR.string.start_matching),
                textColor = Theme.colors.button.onPrimary,
                textStyle = Theme.textStyle.body.small.medium,
                onClick = onClickStartMatching,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                buttonColor = Theme.colors.button.primary,
                cornerRadius = Theme.radius.medium
            )
        }
    }

}

@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_6
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_6
)
@Composable
private fun MatchStartPageContentPreview() {
    CineVerseTheme {
        CompositionLocalProvider(
            LocalScaffoldPaddingValues provides PaddingValues(0.dp)
        ) {
            MatchStartPageContent(
                onClickStartMatching = { }
            )
        }
    }
}
