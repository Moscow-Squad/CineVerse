package com.repository.details

interface DetailsLocalDataSource {
    suspend fun insertFavouriteGenre(genreId: Int)
}