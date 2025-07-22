package com.moscow.cineverse.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

/**
 * A generic PagingSource that takes a suspend lambda to fetch paginated data.
 *
 * @param T The type of the data being loaded.
 * @param fetchData A suspend function that returns a List<T> given a page number.
 */
class BasePagingSource<T : Any>(
    private val fetchData: suspend (page: Int) -> List<T>
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val currentPage = params.key ?: 1
            val data = fetchData(currentPage)

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}
