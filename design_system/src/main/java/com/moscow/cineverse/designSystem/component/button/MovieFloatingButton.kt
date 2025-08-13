package com.moscow.cineverse.designSystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MovieFloatingButton(
    onClick: () -> Unit,
    buttonIcon: Int,
    backgroundColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 40.dp,
    iconSize: Dp = 20.dp,
    enabled: Boolean = true
) {
    Box(
        modifier = modifier
            .size(buttonSize)
            .alpha(if (enabled) 1f else 0.5f)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .clip(RoundedCornerShape(Theme.radius.large))
            .clickable(enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .align(Alignment.Center),
            painter = painterResource(buttonIcon),
            tint = iconColor,
            contentDescription = stringResource(R.string.floating_button_icon)
        )
    }
}

@Preview
@Composable
private fun PreviewButton() {
    MovieFloatingButton(
        buttonIcon = R.drawable.outline_plus,
        onClick = {},
        backgroundColor = Theme.colors.brand.primary,
        iconColor = Color.Black,
    )
}