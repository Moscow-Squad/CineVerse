package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        if (title.isNotEmpty()) {
            Text(
                text = title,
                color = Theme.colors.shade.primary,
                style = Theme.textStyle.title.small,
                maxLines = 1,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Show More",
            color = Theme.colors.brand.primary,
            style = Theme.textStyle.body.medium.medium,
            maxLines = 1,
            modifier = Modifier.clickable { onClick() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SectionTitlePreview() {
    SectionTitle(title = "Action",onClick = {})
}