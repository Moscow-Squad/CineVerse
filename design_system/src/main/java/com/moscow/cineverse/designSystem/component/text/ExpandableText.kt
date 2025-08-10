package com.moscow.cineverse.designSystem.component.text

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.moscow.cineverse.design_system.R
import com.moscow.cineverse.designSystem.theme.Theme
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun ExpandableText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int = 4,
    readMoreText: String = stringResource(id = R.string.readmore),
    readMoreColor:Color = Theme.colors.brand.primary,
    textStyle: TextStyle = Theme.textStyle.body.medium.medium
) {
    val (isExpanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }

    ReadMoreText(
        text = text,
        expanded = isExpanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier
            .clickable { onExpandedChange(!isExpanded) },
        readMoreText = readMoreText,
        readMoreColor = readMoreColor,
        style = textStyle,
        color = Theme.colors.shade.primary,
        readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
        toggleArea = ToggleArea.More,
        readMoreMaxLines = maxLines
    )
}