package com.moscow.cineverse.designSystem.component.button

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun MovieFloatingButton(
    buttonIcon: Int,
    onClick: () -> Unit,
    backgroundColor: Color,
    iconColor: Color,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(10.dp),
) {
    Button(
        modifier = modifier.size(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(Theme.radius.large),
        contentPadding = contentPadding,
        colors = buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Unspecified
        )
    ) {

        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(buttonIcon),
            tint = iconColor,
            contentDescription = stringResource(R.string.floating_button_icon)
        )

    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PreviewButton() {

    MovieFloatingButton(
        R.drawable.outline_plus,
        {},
        Theme.colors.brand.primary,
        iconColor = Color.Black,
    )
}