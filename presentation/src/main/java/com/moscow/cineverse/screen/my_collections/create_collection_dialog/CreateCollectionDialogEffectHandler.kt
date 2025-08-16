package com.moscow.cineverse.screen.my_collections.create_collection_dialog

import android.content.Context
import android.widget.Toast
import com.moscow.cinverse.presentation.R

object CreateCollectionDialogEffectHandler {
    fun handleEffect(
        event: CreateCollectionDialogEvent,
        navigateBack: () -> Unit,
        context: Context
    ) {
        when (event) {
            CreateCollectionDialogEvent.OnCancelCollectionCreation -> {
                navigateBack()
            }

            is CreateCollectionDialogEvent.OnAddCollectionFailed -> {
                Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
            }

            is CreateCollectionDialogEvent.OnCollectionAddedSuccessfully -> {
                Toast.makeText(context, context.getString(R.string.success), Toast.LENGTH_SHORT)
                    .show()
                navigateBack()
            }
        }
    }
}