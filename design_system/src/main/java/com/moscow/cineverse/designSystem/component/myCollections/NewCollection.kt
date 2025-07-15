package com.moscow.cineverse.designSystem.component.myCollections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.AppTextField
import com.moscow.cineverse.designSystem.component.MovieButton
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun NewCollection(
    collectionName: String,
    onCollectionNameChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onCreateClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AppTextField(
            value = collectionName,
            onValueChange = onCollectionNameChange,
            placeholder = "e.g. My Watchlist",
            label = "Collection name",
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.outline_folder),
                    contentDescription = "Folder",
                    tint = Theme.colors.shade.tertiary
                )
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MovieButton(
                buttonText = "Cancel",
                textColor = Theme.colors.button.onSecondary,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onCancelClick,
                buttonColor = Theme.colors.button.secondary,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.5.dp)
            )
            MovieButton(
                buttonText = "Create",
                textColor = Theme.colors.button.onPrimary,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onCreateClick,
                buttonColor = Theme.colors.button.primary,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 14.5.dp)
            )
        }
    }
}

@Preview
@Composable
private fun NewCollectionPreview() {
    var collectionName by remember { mutableStateOf("") }
    NewCollection(
        collectionName = collectionName,
        onCollectionNameChange = { collectionName = it },
        onCancelClick = {},
        onCreateClick = {}
    )
}