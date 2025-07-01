package com.moscow.cineverse.designSystem.component

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieFloatingButton(
    buttonIcon: Painter,
    onClick:()->Unit,
    backgroundColor:Color,
    iconColor:Color,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier.size(56.dp),
        onClick = {onClick()},
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(16.dp),
        colors =buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.Unspecified
        )
    ) {

        Icon(
             modifier = Modifier.size(24.dp),
             painter = buttonIcon,
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
        painterResource(R.drawable.outline_plus),
        {},
        Theme.colors.brand.primary,
        Color.Black,
    )
}