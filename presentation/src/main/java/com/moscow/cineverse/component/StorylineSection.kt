package com.moscow.cineverse.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cinverse.presentation.R


@Composable
fun StorylineSection(
    description: String?,
    modifier: Modifier = Modifier
) {
    val textColor = Theme.colors.shade.secondary

    if (!description.isNullOrEmpty()) {
        Text(
            text = stringResource(R.string.storyline),
            style = Theme.textStyle.title.small,
            color = Theme.colors.shade.primary,
            modifier = modifier.padding(16.dp, top = 24.dp, bottom = 8.dp),
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            overflow = TextOverflow.Ellipsis,
            text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(lineHeight = 12.sp)) {
                    withStyle(
                        style = SpanStyle(
                            color = textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            letterSpacing = 0.sp
                        )
                    ) {
                        append(description)
                    }
                }
            },
            textAlign = TextAlign.Justify
        )
    }

}