package com.moscow.domain.repository

interface OnboardingRepository {
    suspend fun isOnBoardingCompleted(): Boolean
    suspend fun setOnBoardingCompleted()
}