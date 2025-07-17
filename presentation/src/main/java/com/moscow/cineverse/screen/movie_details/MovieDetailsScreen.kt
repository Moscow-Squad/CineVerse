package com.moscow.cineverse.screen.movie_details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastMember
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.Theme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
    movieId: Int,
    modifier: Modifier = Modifier,
    viewModel: MovieDetailsViewModel = koinViewModel(parameters = { parametersOf(movieId) })
) {
    val uiState by viewModel.uiState.collectAsState()
    MovieDetailsContent(
        uiState,
        modifier
    )
}
@Composable
fun MovieDetailsContent(
    uiState: MovieScreenState,
    modifier: Modifier= Modifier
) {
    val textColor = Theme.colors.shade.secondary
    when {
        uiState.movieDetailsUi != null -> {

            Log.d("TAG", "MovieDetailsContent:${uiState.movieDetailsUi} ")
            LazyColumn(
                modifier = Modifier.background(Theme.colors.background.screen)

            ) {
                item { MovieAppBar() }
                item {
                    MovieCardDetails(
                        posterUrl = uiState.movieDetailsUi.posterPath,
                        title = uiState.movieDetailsUi.title,
                        genres = "Drama, Mystery, Sci-Fi & Fantasy",
                        rating = uiState.movieDetailsUi.rating.toString(),
                        duration = "2h 32m",
                        releaseDate = uiState.movieDetailsUi.releaseDate,
                        type = "MOVIE"
                    )
                }
                item {
                    Text(
                        text = stringResource(com.moscow.cinverse.presentation.R.string.storyline),
                        style = Theme.textStyle.title.small,
                        color = Theme.colors.shade.primary,
                        modifier = Modifier.padding(16.dp, top = 24.dp, bottom = 8.dp),
                    )
                }
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        overflow = TextOverflow.Ellipsis,
                        text = buildAnnotatedString {
                            withStyle(style = ParagraphStyle(lineHeight = 12.sp)) {
                                withStyle(
                                    style = SpanStyle(
                                        color = textColor,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp,
                                        letterSpacing = 0.sp
                                    )
                                ) {
                                    append(uiState.movieDetailsUi.description)
                                }
                            }
                        },
                        textAlign = TextAlign.Justify
                    )
                }
                item {
                    StarCastSection(
                        modifier = Modifier
                            .background(Theme.colors.background.screen)
                            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                        seeMore = {},
                        cast = listOf(
                            CastMember(
                                realName = "John Doe",
                                nameInMovie = "John",
                                imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                            ),
                            CastMember(
                                realName = "Jane Smith",
                                nameInMovie = "Jane",
                                imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                            ),
                            CastMember(
                                realName = "Alice Johnson",
                                nameInMovie = "Alice",
                                imageUrl = "https://wror.com/uploads/2025/05/GettyImages-1511406162.jpg?format=auto&optimize=high&width=1440"
                            ),
                            CastMember(
                                realName = "Bob Brown",
                                nameInMovie = "Bob",
                                imageUrl = null
                            ),
                            CastMember(
                                realName = "Charlie White",
                                nameInMovie = "Charlie",
                                imageUrl = null
                            ),

                            )
                    )
                }
                item {
                    StaffInfoSection(
                        staffInfo = listOf(
                            "Director" to "John Doe",
                            "Director, Screenplay, Story" to "Christopher Nolan",
                            "Producer" to "Jane Smith",
                            "Writer" to "Alice Johnson"
                        ),
                        modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    )
                }

                item {
                    MovieListSection(
                        title = "You Might Also Like",
                        movies = listOf(
                            Movie(
                                id = 1,
                                title = "The Crimson Man",
                                posterUrl = "",
                                rating = 8.8f,
                                genres = listOf("Action", "Sci-Fi"),
                                duration = "2h 28m",
                                releaseDate = "2010-07-16"
                            ),
                            Movie(
                                id = 2,
                                title = "Interstellar",
                                posterUrl = "",
                                rating = 9.9f,
                                genres = listOf("Adventure", "Drama", "Sci-Fi"),
                                duration = "2h 49m",
                                releaseDate = "2014-11-07"
                            ),
                            Movie(
                                id = 4,
                                title = "PK",
                                posterUrl = "",
                                rating = 8.8f,
                                genres = listOf("Action", "Sci-Fi"),
                                duration = "2h 28m",
                                releaseDate = "2010-07-16"
                            ),
                        ),
                        onClickShowMore = { },
                        onClickPoster = { },
                        modifier = Modifier.padding(top = 16.dp),
                        movieCardContent = { movie, modifier, onClick ->
                        }
                    )
                }
                item {
                    RatingSection(
                        icon = R.drawable.due_tone_star,
                        title = stringResource(com.moscow.cinverse.presentation.R.string.give_it_stars),
                        caption = stringResource(com.moscow.cinverse.presentation.R.string.let_the_world_know_how_you_felt),
                        onClickArrow = {},
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    )
                }

                item {

                    SectionTitle(
                        title = stringResource(com.moscow.cinverse.presentation.R.string.top_reviews),
                        onClick = {},
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 12.dp,
                            top = 24.dp
                        )
                    )
                    repeat(uiState.reviewsFlow?.size?:0){
                        MovieReviewCard(
                            uiState.reviewsFlow?.get(it)?.name?:"",
                            uiState.reviewsFlow?.get(it)?.username?:"",
                            uiState.reviewsFlow?.get(it)?.reviewContent?:"",
                            uiState.reviewsFlow?.get(it)?.rate?:0,
                            uiState.reviewsFlow?.get(it)?.date?:"",
                            painterResource(R.drawable.outline_user),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                        )

                    }
                }
            }
        }

    }
}

/*
@Preview(showBackground = true)
@Composable
private fun MovieDetailsScreenPreview() {
    CineVerseTheme {
        MovieDetailsScreen()
    }

}*/
