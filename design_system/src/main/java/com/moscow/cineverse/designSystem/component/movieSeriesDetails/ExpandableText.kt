package com.moscow.cineverse.designSystem.component.movieSeriesDetails

import androidx.compose.foundation.clickable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 4,
    readMoreText: String = stringResource(R.string.readmore),
    readMoreColor:Color = Theme.colors.brand.primary,
    textStyle: TextStyle = Theme.textStyle.body.medium.medium

    ) {
    val (isExpanded,onExpandedChange) = rememberSaveable { mutableStateOf(false) }

    ReadMoreText(
        text = text,
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .clickable { onExpandedChange(!isExpanded)},
        readMoreText = readMoreText,
        readMoreColor = readMoreColor,
        style = textStyle,
        color = Theme.colors.shade.primary,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        toggleArea = ToggleArea.More,
        readMoreMaxLines = maxLines


    )
    /*Box(modifier = Modifier.fillMaxWidth()) {
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
    }*/
}