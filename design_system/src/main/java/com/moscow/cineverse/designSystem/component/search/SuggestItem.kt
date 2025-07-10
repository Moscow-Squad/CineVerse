package com.moscow.cineverse.designSystem.component.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SuggestItem(
    title: String,
    @DrawableRes icon: Int,
    modifier : Modifier = Modifier
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(icon),
            contentDescription = stringResource(R.string.suggest_item_icon),
            tint = Theme.colors.shade.secondary
        )
        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = Theme.textStyle.body.medium.medium,
            color = Theme.colors.shade.secondary,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(Theme.icons.outline.arrowLeftUp),
            contentDescription = stringResource(R.string.arrow_left_up),
            tint = Theme.colors.shade.secondary
        )
    }
}

@Preview
@Composable
private fun SuggestItemPreview() {
    SuggestItem(
        "dddddddddddd",
        Theme.icons.outline.search
    )
}