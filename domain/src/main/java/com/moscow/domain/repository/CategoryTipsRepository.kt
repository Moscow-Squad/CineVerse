package com.moscow.domain.repository

interface CategoryTipsRepository {
    suspend fun showCategoryDetailsTip(): Boolean
    suspend fun closeCategoryDetailsTip()
}