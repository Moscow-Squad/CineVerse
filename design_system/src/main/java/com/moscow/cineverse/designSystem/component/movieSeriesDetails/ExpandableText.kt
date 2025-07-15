package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun ExpandableText(
    text: String,
    minimizedMaxLines: Int = 4
) {
    var isExpanded by remember { mutableStateOf(false) }
    var shouldShowToggle by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            maxLines = minimizedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = Theme.textStyle.body.medium.medium,
            onTextLayout = { layoutResult ->
                shouldShowToggle = layoutResult.hasVisualOverflow ||
                        layoutResult.lineCount > minimizedMaxLines
            },
            modifier = Modifier.alpha(0f)
        )

        val annotatedText = buildAnnotatedString {
            append(text)

            if (shouldShowToggle && isExpanded) {
                append(" ")

                pushStringAnnotation(
                    tag = "TOGGLE",
                    annotation = "toggle"
                )
                withStyle(

                    style = SpanStyle(
                        color = Theme.colors.brand.primary,
                    )
                ) {
                    append("Show Less")
                }
                pop()
            }
        }

        Column {
            ClickableText(
                text = annotatedText,
                style = Theme.textStyle.body.medium.medium.copy(
                    color = Theme.colors.shade.primary
                ),
                maxLines = if (isExpanded) Int.MAX_VALUE else minimizedMaxLines,
                overflow = TextOverflow.Ellipsis,
                onClick = { offset ->
                    if (isExpanded) {
                        annotatedText
                            .getStringAnnotations(tag = "TOGGLE", start = offset, end = offset)
                            .firstOrNull()?.let {
                                isExpanded = false
                            }
                    }
                },
                modifier = Modifier.animateContentSize()
            )

            if (shouldShowToggle && !isExpanded) {
                Text(
                    text = "Read More",
                    style = Theme.textStyle.body.small.regular.copy(
                        color = Theme.colors.brand.primary,
                    ),
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .clickable { isExpanded = true }
                )
            }
        }
    }
}