package com.moscow.domain.repository.blur

import kotlinx.coroutines.flow.Flow

interface BlurProvider {
    suspend fun changeBlur(enabled: Boolean)
    suspend fun clearBlur()
    val blurFlow: Flow<Boolean>
}
