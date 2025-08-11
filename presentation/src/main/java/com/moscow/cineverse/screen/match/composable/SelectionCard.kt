package com.moscow.cineverse.screen.match.composable

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.match.QuestionUiState
import com.moscow.cinverse.presentation.R

@Composable
fun SelectionCard(
    onClick: () -> Unit,
    questionUiState: QuestionUiState,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = Theme.colors.background.card,
    cardSelectionBackgroundColor: Color = Theme.colors.brand.tertiary,
    borderSelectionColor: Color = Theme.colors.brand.secondary,
    iconBackgroundColor: Color = Theme.colors.brand.tertiary,
    iconSelectionBackgroundColor: Color = Theme.colors.brand.secondary,
) {
    val backgroundColor by animateColorAsState(
        if (questionUiState.isSelected) cardSelectionBackgroundColor
        else cardBackgroundColor,
    )
    val borderColor by animateColorAsState(
        if (questionUiState.isSelected) borderSelectionColor
        else Color.Transparent,
    )
    val textColor by animateColorAsState(
        if (questionUiState.isSelected) Theme.colors.brand.primary
        else Theme.colors.shade.primary
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.large))
            .background(backgroundColor)
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(Theme.radius.large)
            )
            .padding(12.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        questionUiState.iconResource?.let {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(Theme.radius.medium))
                    .background(
                        if (questionUiState.isSelected) iconSelectionBackgroundColor
                        else iconBackgroundColor
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(questionUiState.iconResource),
                    contentDescription = null,
                    tint = Theme.colors.brand.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }



        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = textColor)) {
                    append(questionUiState.name)
                }
                questionUiState.description?.let {
                    withStyle(style = SpanStyle(color = Theme.colors.shade.primary)) {
                        append(" (${questionUiState.description})")
                    }
                }
            },
            style = Theme.textStyle.body.medium.medium,
        )
    }
}

@Preview(
    name = "Card Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
)
@Preview(
    name = "Card Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun SelectionCardPreview() {
    CineVerseTheme {
        var isSelected by remember { mutableStateOf(false) }

        SelectionCard(
            questionUiState = QuestionUiState(
                id = 1,
                name = "Option 1",
                iconResource = R.drawable.folder_icon
            ),
            onClick = { isSelected = !isSelected }
        )
    }
}