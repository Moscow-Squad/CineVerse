package com.moscow.cineverse.designSystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.preview.CineVersePreviews
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MovieFloatingButton(
    onClick: () -> Unit,
    buttonIcon: Int,
    backgroundColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(all = 10.dp),
    useWrapContentSize: Boolean = false,
    buttonSize: Dp = 40.dp,
    iconSize: Dp = 20.dp,
) {
    Button(
        modifier = if (useWrapContentSize) modifier.size(buttonSize) else modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Theme.radius.large),
        contentPadding = contentPadding,
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Unspecified
        )
    ) {

        Icon(
            modifier = Modifier.size(iconSize),
            painter = painterResource(buttonIcon),
            tint = iconColor,
            contentDescription = stringResource(R.string.floating_button_icon)
        )

    }
}

@CineVersePreviews
@Composable
private fun PreviewButton() {

    MovieFloatingButton(
        buttonIcon = R.drawable.outline_plus,
        onClick = {},
        backgroundColor = Theme.colors.brand.primary,
        iconColor = Color.Black,
    )

}