package com.moscow.domain.repository

interface RatingTipsRepository {
     suspend fun showRatingTip(): Boolean
     suspend fun closeRatingTip()
}