package com.moscow.cineverse.designSystem.component.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun OnBlurContent(
    modifier: Modifier = Modifier,
    isAddedText: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = Color(0x52000000)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_eye_slash),
            contentDescription = null,
            modifier = Modifier.size(size = 24.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = Color(0xFFE1E1E3)
            ),
        )
        if (isAddedText) Text(
            text = stringResource(id = R.string.sensitive_content),

            style = Theme.textStyle.body.small.medium.copy(
                color = Color(0xFFE1E1E3)
            ),
            textAlign = TextAlign.Center
        )
    }
}