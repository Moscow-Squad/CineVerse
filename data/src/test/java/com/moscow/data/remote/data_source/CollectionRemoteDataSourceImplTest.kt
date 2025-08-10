package com.moscow.data.remote.data_source

import com.google.common.truth.Truth.assertThat
import com.moscow.data_source.remote.CollectionRemoteDataSource
import com.moscow.domain.exception.CineVerseException
import com.moscow.remote.data_source.CollectionRemoteDataSourceImpl
import com.moscow.remote.dto.AddCollectionDto
import com.moscow.remote.dto.AddMediaItemToCollectionRequestDto
import com.moscow.remote.dto.CollectionDto
import com.moscow.remote.dto.CreateCollectionDto
import com.moscow.remote.dto.MovieDto
import com.moscow.remote.services.CollectionsService
import com.moscow.utils.ApiResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class CollectionRemoteDataSourceImplTest {

    private val collectionService: CollectionsService = mockk()
    private lateinit var collectionRemoteDataSource: CollectionRemoteDataSource

    @BeforeEach
    fun setup() {
        collectionRemoteDataSource = CollectionRemoteDataSourceImpl(collectionService)
    }


    @Test
    fun `given accountId and sessionId when getMyCollections returns success should return my collections`() = runTest {
        val accountId = "123"
        val sessionId = "abc"
        val page = 1
        val expected = ApiResponse<CollectionDto>(
            results = listOf(
                CollectionDto(
                    name = "New Collection"
                )
            )
        )

        coEvery { collectionService.getMyCollections(accountId, page, sessionId) } returns Response.success(expected)

        val result = collectionRemoteDataSource.getMyCollections(accountId, sessionId, page)

        assertThat(expected).isEqualTo( result)
        coVerify (exactly = 1){
            collectionService.getMyCollections(accountId, page, sessionId)
        }
    }

    @Test
    fun `when getMyCollections fails should throws exception`() = runTest {
        val accountId = "123"
        val sessionId = "abc"
        val page = 1

        val errorResponse = Response.error< ApiResponse<CollectionDto>>(
            500,
            "Internal Server Error".toResponseBody("application/json".toMediaType())
        )

        coEvery { collectionService.getMyCollections(accountId, page, sessionId) } throws HttpException(errorResponse)

        assertThrows<CineVerseException> {
            collectionRemoteDataSource.getMyCollections(accountId, sessionId, page)
        }
        coVerify (exactly = 1){
            collectionService.getMyCollections(accountId, page, sessionId)
        }
    }

    @Test
    fun `given sessionId and new collection when addNewCollection returns success then add new collection`() = runTest {
        val sessionId = "abc"
        val dto = CreateCollectionDto(name = "New Collection", description = null)
        val response = AddCollectionDto(122,201,"",true)

        coEvery { collectionService.addNewCollection(dto, sessionId) } returns Response.success(response)

        val result = collectionRemoteDataSource.addNewCollection(dto, sessionId)

        assertThat(response).isEqualTo(result)
        coVerify (exactly = 1){
            collectionService.addNewCollection(dto, sessionId)
        }
    }

    @Test
    fun `given sessionId and collectionId when addMediaItemToCollection returns success then add new media item to selected collection`() = runTest {
        val sessionId = "abc"
        val collectionId = 123
        val dto = AddMediaItemToCollectionRequestDto(mediaId = 1)
        val response = ApiResponse<Unit>()

        coEvery { collectionService.addMediaItemToCollection(dto, collectionId , sessionId) } returns Response.success(response)

        val result = collectionRemoteDataSource.addMediaItemToCollection(dto,collectionId, sessionId)

        assertThat(response).isEqualTo(result)
        coVerify (exactly = 1){
            collectionService.addMediaItemToCollection(dto, collectionId , sessionId)
        }
    }

     @Test
    fun `given sessionId and collectionId when getCollectionDetails returns success then return collection details`() = runTest {
        val sessionId = "abc"
        val collectionId = 123
        val dto = MovieDto(id = 1, name = "Batman")
        val response = ApiResponse<MovieDto>(
            results = listOf(dto)
        )

        coEvery { collectionService.getCollectionDetails(collectionId , sessionId,1) } returns Response.success(response)

        val result = collectionRemoteDataSource.getCollectionDetails(collectionId, sessionId,1)

        assertThat(response).isEqualTo(result)
        coVerify (exactly = 1){
            collectionService.getCollectionDetails( collectionId , sessionId,1)
        }
    }



}