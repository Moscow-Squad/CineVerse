package usecase

import com.android.domain.exception.CineVerseException.NoSuggestionFoundException
import com.android.domain.repository.MovieRepository
import com.android.domain.usecase.GetLocalSuggestions
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Test

class GetLocalSuggestionsTest {
    private val movieRepository: MovieRepository = mockk<MovieRepository>(relaxed = true)
    private lateinit var getLocalSuggestions: GetLocalSuggestions

    @BeforeEach
    fun setup() {
        getLocalSuggestions = GetLocalSuggestions(movieRepository)
    }

    @Test
    fun `should return list when repository returns list`() = runTest{
        //Given
        val res = listOf("suggest1", "suggest2", "suggest3")
        //when
        coEvery { movieRepository.getLocalSuggestions() } returns flowOf(res)
        val testRes = getLocalSuggestions().first()
        //then
        assertThat(testRes).isEqualTo(res)
    }

    @Test
    fun `should throw NoSuggestionFoundException when repository returns empty list`() = runTest{
        //Given & when
        coEvery { movieRepository.getLocalSuggestions() } returns flowOf(emptyList<String>())
        //then
        assertThrows<NoSuggestionFoundException> {
            getLocalSuggestions()
        }
    }

    @Test
    fun `should throws exception when repository throws exception`() = runTest{
        //Given & when
        coEvery { movieRepository.getLocalSuggestions() } throws Exception()
        //then
        assertThrows<Exception> {
            getLocalSuggestions()
        }
    }
}