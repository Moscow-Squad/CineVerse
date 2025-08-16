package com.moscow.cineverse.screen.cast_detials.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.utlis.noRibbleClick
import com.moscow.cinverse.presentation.R

@Composable
fun SocialMediaPill(
    name: String,
    iconRes: Int,
    url: String?,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    iconColor:Color = Color.Unspecified,
    ) {
    val (backgroundColor, textColor) = when (name.lowercase()) {
        "youtube" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "facebook" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "instagram" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "twitter" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        "tiktok" -> Theme.colors.shade.quinary to Theme.colors.shade.primary
        else -> Theme.colors.shade.quinary to Theme.colors.shade.primary
    }

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(backgroundColor)
            .noRibbleClick { url?.let { onClick(it) } }
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Image(
            painter = painterResource(id = iconRes),
            modifier = Modifier.size(16.dp),
            contentDescription = "$name icon",
            colorFilter = if (iconColor != Color.Unspecified) {
                ColorFilter.tint(iconColor)
            } else {
                null
            }
        )

        Text(
            text = name,
            color = textColor,
            style = Theme.textStyle.label.medium.medium,
        )
    }
}

@Preview
@Composable
private fun SocialMediaPillPreview() {

    SocialMediaPill(
        name = stringResource(R.string.tik_tok),
        iconRes = R.drawable.colored_tiktok,
        url = "",
        onClick = { },
    )
}
