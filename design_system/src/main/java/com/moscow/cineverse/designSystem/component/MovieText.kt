package com.moscow.cineverse.designSystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun MovieText(
    modifier: Modifier = Modifier,
    text: String = "Movie Title",
    color: Color = Theme.colors.shade.primary,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = Theme.textStyle.label.medium.medium
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow,
        style = style
    )
}

@Preview(showBackground = true)
@Composable
fun MovieTextPreview() {
    CineVerseTheme {
        MovieText(
            text = "Sample Movie Title",
            modifier = Modifier.padding(16.dp),
            color = Theme.colors.shade.primary,
            style = Theme.textStyle.label.medium.medium
        )
    }
}