package com.moscow.cineverse.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    actionTitle: String? = stringResource(R.string.show_more)
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.title.small,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        if (actionTitle != null){
            Text(
                text = actionTitle,
                color = Theme.colors.brand.primary,
                style = Theme.textStyle.body.medium.medium,
                maxLines = 1,
                modifier = Modifier.clickable { onClick() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionTitlePreview() {
    SectionTitle(title = "Action",onClick = {})
}