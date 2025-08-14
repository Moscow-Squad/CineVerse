package com.moscow.cineverse.screen.collections

import android.content.Context
import android.widget.Toast

object CollectionsBottomSheetEffectHandler {
    fun handleEffect(
        event: CollectionsBottomSheetEffect,
        onCreateCollectionClicked: () -> Unit,
        navigateBack: () -> Unit,
        onLogIn: () -> Unit,
        context: Context
    ) {
        when (event) {
            CollectionsBottomSheetEffect.OnCreateCollectionClicked -> {
                onCreateCollectionClicked()
            }

            CollectionsBottomSheetEffect.OnLoginClicked -> {
                onLogIn()
            }

            is CollectionsBottomSheetEffect.OnItemAddedSuccessfully -> {
                Toast.makeText(context, "item added successfully", Toast.LENGTH_SHORT).show()
                navigateBack()
            }

            is CollectionsBottomSheetEffect.OnItemAddedFailed -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}