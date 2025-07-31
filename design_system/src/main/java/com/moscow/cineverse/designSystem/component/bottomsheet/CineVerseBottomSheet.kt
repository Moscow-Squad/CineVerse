package com.moscow.cineverse.designSystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.message_info.MessageInfoBox
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.designSystem.theme.ThemeState
import com.moscow.cineverse.design_system.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineVerseBottomSheet(
    title: String = "",
    modifier: Modifier = Modifier,
    expanded: Boolean = true,
    showCancelIcon: Boolean = true,
    containerColor: Color = Theme.colors.background.bottomSheet,
    scrimColor: Color = Theme.colors.background.bottomSheetContainer,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: () -> Unit = {},
    onClose: () -> Unit = {},
    onAddNewCollectionClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = expanded)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = containerColor,
        scrimColor = scrimColor,
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        modifier = modifier
            .padding(horizontal = 12.dp)
            .navigationBarsPadding(),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .size(width = 48.dp, height = 3.dp)
                    .background(
                        color = Theme.colors.shade.quaternary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        },
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = true
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = contentHorizontalAlignment
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (title.isNotEmpty()) {
                    Text(
                        text = title,
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary
                    )
                }
                when {
                    showCancelIcon -> {
                        Icon(
                            painter = painterResource(R.drawable.outline_x),
                            contentDescription = null,
                            tint = Theme.colors.shade.secondary,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                                .clickable { onClose() }
                        )
                    }

                    title.isNotEmpty() -> {
                        MovieText(
                            text = stringResource(R.string.new_collection),
                            style = Theme.textStyle.body.medium.medium,
                            color = Theme.colors.brand.primary,
                            modifier = Modifier.clickable(onClick = onAddNewCollectionClick)
                        )
                    }
                }
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CineVerseBottomSheetPreview() {
    CineVerseTheme {

        var show by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background.screen)
                .padding(100.dp),
        ) {
            Button(
                onClick = { show = true }) {
                Text("Show Bottom Sheet")
            }

            if (show) {
                CineVerseTheme(state = ThemeState(isDark = true) {}) {
                    CineVerseBottomSheet(
                        title = "Add to Collection",
                        onClose = { show = false },
                        onDismissRequest = { show = false },
                        showCancelIcon = true,
                        content = {
                            MessageInfoBox(
                                title = "No Collections Yet",
                                description = "Create a new collection to start saving your favorite movies and series.",
                                icon = painterResource(Theme.icons.dueTone.videoLibrary),
                                showButtonsGroup = false,
                                firstButtonText = "",
                                onClickFirstButton = {},
                                secondButtonText = "Create Collection",
                                onClickSecondButton = {
                                    /* TODO("should open new bottom sheet to login or to create new collection") */
                                },
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }
                    )
                }
            }
        }
    }
}