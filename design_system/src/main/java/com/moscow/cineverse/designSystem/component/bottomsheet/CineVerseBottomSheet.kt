package com.moscow.cineverse.designSystem.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.design_system.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CineVerseBottomSheet(
    modifier: Modifier = Modifier,
    title: String = "",
    expanded: Boolean = true,
    showCancelIcon: Boolean = true,
    containerColor: Color = Theme.colors.background.bottomSheet,
    contentHorizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    onDismissRequest: () -> Unit = {},
    onClose: () -> Unit = {},
    onAddNewCollectionClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = expanded
    )

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        containerColor = containerColor,
        shape = RoundedCornerShape(size = Theme.radius.extraLarge),
        modifier = modifier
            .padding(all = 12.dp)
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
        val bottomPadding = if (title.isNotEmpty()) Modifier.padding(bottom = 20.dp) else Modifier
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = contentHorizontalAlignment
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then( bottomPadding),
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
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .clickable { onClose() }
                        , contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(R.drawable.outline_x),
                                contentDescription = null,
                                tint = Theme.colors.shade.secondary,
                                modifier = Modifier
                                    .size(20.dp)
                            )
                        }
                    }

                    title.isNotEmpty() -> {
                        MovieText(
                            text = stringResource(id = R.string.new_collection),
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
