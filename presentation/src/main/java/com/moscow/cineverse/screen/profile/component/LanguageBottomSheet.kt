package com.moscow.cineverse.screen.profile.component

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.bottomsheet.CineVerseBottomSheet
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R

@Composable
fun LanguageBottomSheet(
    visible: Boolean,
    selectedLanguage: String,
    onDismiss: () -> Unit,
    onSelectedLanguage: (String) -> Unit
) {
    AnimatedVisibility(visible) {
        CineVerseBottomSheet(
            title = stringResource(R.string.change_language),
            onClose = onDismiss,
            onDismissRequest = onDismiss,
            showCancelIcon = true,
        ) {
            LanguageBottomSheetContent(
                selectedLanguage
            ) { newLanguage ->
                onSelectedLanguage(newLanguage)
            }
        }
    }
}

@Composable
private fun LanguageBottomSheetContent(
    selectedLanguage: String,
    onSelectLanguage: (String) -> Unit,
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
    ) {
        LanguageOption(
            modifier = Modifier.weight(1f),
            name = stringResource(R.string.english),
            imageRes = R.drawable.uk_flag,
            isSelected = selectedLanguage == "en",
            onClick = { onSelectLanguage("en") }
        )
        LanguageOption(
            modifier = Modifier.weight(1f),
            name = stringResource(R.string.arabic),
            imageRes = R.drawable.colored_iraq_flag,
            isSelected = selectedLanguage == "ar",
            onClick = { onSelectLanguage("ar") }
        )
    }
}


@Composable
fun LanguageOption(
    modifier: Modifier = Modifier,
    name: String,
    imageRes: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    val borderColor by animateColorAsState(
        if (isSelected) Theme.colors.brand.secondary else Color.Transparent
    )

    val backgroundColor by animateColorAsState(
        if (isSelected) Theme.colors.brand.tertiary else Theme.colors.background.bottomSheetCard
    )

    Column(
        modifier = modifier
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
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = "$name flag",
            modifier = Modifier
                .size(32.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            color = if (isSelected) Theme.colors.brand.primary else Theme.colors.shade.primary,
            style = Theme.textStyle.body.medium.medium
        )
    }
}
