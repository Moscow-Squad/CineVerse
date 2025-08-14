package com.moscow.domain.service.blur

import kotlinx.coroutines.flow.Flow

interface BlurProvider {
    suspend fun changeBlur(enabled: String)
    suspend fun clearBlur()
    val blurFlow: Flow<String>
}
