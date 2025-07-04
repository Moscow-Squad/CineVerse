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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.design_system.R
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CineVerseBottomSheet(
    title: String,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Theme.colors.background.bottomSheet,
    expanded: Boolean = true,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: (() -> Unit)?,
    scrimColor: Color = Theme.colors.background.bottomSheetContainer,
    content: @Composable ColumnScope.() -> Unit,
) {

    val sheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = expanded,
        )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest ?: {},
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = onDismissRequest != null),
        containerColor = containerColor,
        scrimColor = scrimColor,
        shape = RoundedCornerShape(Theme.radius.extraLarge),
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .padding(bottom = 28.dp),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 20.dp)
                    .width(48.dp)
                    .height(3.dp)
                    .background(
                        color = Theme.colors.shade.quaternary,
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    ) {

        Column(
            horizontalAlignment = contentHorizontalAlignment,
            modifier =
                modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = Theme.textStyle.title.small,
                    color = Theme.colors.shade.primary
                )
                Icon(
                    painter = painterResource(R.drawable.outline_x),
                    contentDescription = stringResource(R.string.close_bottom_sheet),
                    tint = Theme.colors.shade.secondary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onClose() }
                )
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
                onClick = { show = true }
            ) {
                Text("Show Bottom Sheet")
            }

            if (show) {
                CineVerseBottomSheet(
                    title = "Title",
                    onClose = { show = false },
                    onDismissRequest = { show = false },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(Theme.radius.large))
                                .background(Theme.colors.background.bottomSheetCard),
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ) {

                        }
                    }
                )
            }
        }
    }

}