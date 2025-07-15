package com.moscow.cineverse.screen.series_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.design_system.R
import com.moscow.cineverse.designSystem.component.Movie
import com.moscow.cineverse.designSystem.component.MovieAppBar
import com.moscow.cineverse.designSystem.component.MovieListSection
import com.moscow.cineverse.designSystem.component.SectionTitle
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.CastMember
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieCardDetails
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.MovieReviewCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.RatingSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.Season
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.SeasonCard
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StaffInfoSection
import com.moscow.cineverse.designSystem.component.movieSeriesDetails.StarCastSection
import com.moscow.cineverse.designSystem.theme.CineVerseTheme
import com.moscow.cineverse.designSystem.theme.Theme

@Composable
fun SeriesDetailsScreen() {
    val textColor = Theme.colors.shade.secondary
    LazyColumn(
        modifier = Modifier.background(Theme.colors.background.screen)
    ) {
        item { MovieAppBar() }
        item {
            MovieCardDetails(
                posterUrl = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                title = "Supernatural",
                genres = "Drama, Mystery, Sci-Fi & Fantasy",
                rating = "8.5",
                duration = "2h 32m",
                releaseDate = "2008, Jul 18",
                type = "SERIES"
            )
        }
        item {
            Text(
                text = "Storyline",
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
                            append(
                                "When they were boys, Sam and Dean Winchester lost their mother to a mysterious and demonic supernatural force. " +
                                        "Subsequently, their father raised them to be soldiers. He taught them about the paranormal evil that lives in the dark corners " +
                                        "and on the back roads of America ... and he taught them how to kill it. Now, the Winchester brothers crisscross the country " +
                                        "in their '67 Chevy Impala, battling every kind of supernatural threat they encounter along the way."
                            )
                        }
                    }
                },
                textAlign = TextAlign.Justify
            )
        }

        item {
            SectionTitle(
                title = "Latest Seasons",
                onClick = {},
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 24.dp)
            )
        }
        item {
            SeasonCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                season = Season(
                    seasonNumber = 1,
                    episodeCount = 10,
                    airDate = "2019",
                    posterUrl = "https://www.dreamworks.com/storage/cms-uploads/the-wild-robot-poster4.jpg",
                    caption = "Season 1 of the series",
                    rate = 8.5f
                )
            )
        }

        item {
            StarCastSection(
                modifier = Modifier
                    .background(Theme.colors.background.screen)
                    .padding(10.dp),
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
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp)
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
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        item {
            RatingSection(
                icon = R.drawable.due_tone_star,
                title = "Give it Stars!",
                caption = "Let the world know how you felt.",
                onClickArrow = {},
                modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp)
            )
        }
        item {
            SectionTitle(
                title = "Top Reviews",
                onClick = {},
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp, top = 24.dp)
            )
        }
        item {
            MovieReviewCard(
                "Shrouk Mohamed",
                "@ShroukMohamed16",
                "Lorem Ipsum is simply dummy text of the printing and typesetting industry.Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                3,
                "Aug 15, 2025",
                painterResource(R.drawable.outline_user),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            )
        }
    }
}

@Preview
@Composable
private fun SeriesDetailsScreenPreview() {
    CineVerseTheme {
        SeriesDetailsScreen()
    }
}