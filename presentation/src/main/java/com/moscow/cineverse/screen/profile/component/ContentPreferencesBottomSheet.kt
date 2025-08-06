package com.moscow.cineverse.screen.profile.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun ContentPreferencesBottomSheet(
    visible: Boolean,
    onDismiss: () -> Unit,
    onClickPreference: (String) -> Unit
) {
    AnimatedVisibility(visible) {
        CineVerseBottomSheet(
            title = stringResource(R.string.content_preferences),
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            showCancelIcon = true,
        ) {
            ContentPreferencesContent(
                selectedPreference = "",
                onPreferenceChanged ={}

            )


        }
    }
}

@Composable
fun ContentPreferencesContent(
    modifier: Modifier = Modifier,
    selectedPreference:String,
    onPreferenceChanged:(String) -> Unit

) {
    Column (
        modifier = modifier.padding(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Text(
            text = stringResource(R.string.choose_how_you_d_like_to_handle_sensitive_or_revealing_images_in_the_app),
            color = Theme.colors.shade.secondary,
            style = Theme.textStyle.body.small.regular
        )

        PreferenceOption(
            name = stringResource(R.string.hide_explicit_content),
            description = stringResource(R.string.hides_revealing_or_inappropriate_posters_e_g_nudity_strong_sexual_content),
            imageRes = painterResource(com.moscow.cineverse.design_system.R.drawable.icon_eye_slash),
            isSelected = true,
            onClick = { }

        )
        PreferenceOption(
            name = stringResource(R.string.strict_filtering),
            description = stringResource(R.string.hides_all_content_that_includes_immodest_clothing_or_behavior),
            imageRes = painterResource(com.moscow.cineverse.design_system.R.drawable.icon_eye_slash),
            isSelected = false,
            onClick = { }

        )
        PreferenceOption(
            name = stringResource(R.string.show_all_content),
            description = stringResource(R.string.no_filtering_all_images_and_posters_will_be_displayed),
            imageRes = painterResource(com.moscow.cineverse.design_system.R.drawable.icon_eye_slash),
            isSelected = false,
            onClick = { }

        )
    }

}

@Composable
fun PreferenceOption(
    modifier: Modifier = Modifier,
    name: String,
    description:String,
    imageRes: Painter,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        if (isSelected) Theme.colors.brand.secondary else Color.Transparent
    )

    val backgroundColor by animateColorAsState(
        if (isSelected) Theme.colors.brand.tertiary else Theme.colors.background.bottomSheetCard
    )

    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .clickable { onClick() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Image(
            painter = imageRes,
            contentDescription = "preference icon",
            colorFilter = ColorFilter.tint(if (isSelected) Theme.colors.brand.secondary else Theme.colors.shade.quaternary),
            modifier = Modifier
                .size(32.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.primary,
                style = Theme.textStyle.body.medium.medium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                color = Theme.colors.shade.secondary,
                style = Theme.textStyle.body.medium.medium
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,)
@Composable
private fun PreviewPreference() {
    CineVerseTheme {
         ContentPreferencesBottomSheet(
            visible = true,
            onDismiss = {},
            onClickPreference= {}
        )
    }
}