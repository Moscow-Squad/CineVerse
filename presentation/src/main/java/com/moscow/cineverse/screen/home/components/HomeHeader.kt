package com.moscow.cineverse.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@Composable
fun HomeHeader(userName: String?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth()
            .padding(start = 16.dp, end = 12.dp, top = 20.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Image(
            painter = painterResource(R.drawable.colored_cineverse_logo),
            contentDescription = null,
            modifier = Modifier

        )

        Column(
            modifier = Modifier.weight(1f),
        ) {
            if (userName != null){
                Text(
                    text = stringResource(com.moscow.cinverse.presentation.R.string.welcome),
                    style = Theme.textStyle.body.small.regular,
                    color = Theme.colors.shade.secondary
                )
                Text(
                    text = userName,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
            }else{
                Text(
                    text = stringResource(com.moscow.cinverse.presentation.R.string.Home),
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
            }
        }
    }
}