package com.moscow.cineverse.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.domain.model.Movie
import com.android.domain.model.Review

class RecommendationsMoviesPagingSource(
    private val getRecommendationsMovies: suspend (page: Int) -> List<Movie>
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: 1
            val pages = getRecommendationsMovies(position)

            LoadResult.Page(
                data = pages,
                prevKey = if (position == 1) null else (position - 1),
                nextKey = if (pages.isEmpty()) null else (position + 1)
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}