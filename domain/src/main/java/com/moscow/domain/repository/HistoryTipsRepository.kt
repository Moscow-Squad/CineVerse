package com.moscow.domain.repository

interface HistoryTipsRepository {
    suspend fun showHistoryTip(): Boolean
    suspend fun closeHistoryTip()
}