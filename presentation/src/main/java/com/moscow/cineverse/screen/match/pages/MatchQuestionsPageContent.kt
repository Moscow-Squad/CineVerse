package com.moscow.cineverse.screen.match.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.moscow.cineverse.designSystem.component.app_bar.MovieAppBar
import com.moscow.cineverse.designSystem.component.button.MovieButton
import com.moscow.cineverse.designSystem.component.wrapper.MovieScaffold
import com.moscow.cineverse.designSystem.component.wrapper.MovieText
import com.moscow.cineverse.designSystem.theme.Theme
import com.moscow.cineverse.screen.match.MatchUiState
import com.moscow.cineverse.screen.match.QuestionType
import com.moscow.cineverse.screen.match.QuestionUiState
import com.moscow.cineverse.screen.match.composable.ProgressIndicator
import com.moscow.cineverse.screen.match.composable.QuestionAndAnswersLayout
import com.moscow.cinverse.presentation.R

@Composable
fun MatchQuestionsPageContent(
    state: MatchUiState,
    modifier: Modifier = Modifier,
    onClickNextQuestion: () -> Unit,
    onAnswerSelected: (type: QuestionType, answer: QuestionUiState) -> Unit,
    onNavigateBack: () -> Unit,
    nextButtonTextColor: Color,
    nextButtonColor: Color,
) {
    MovieScaffold(
        movieAppBar = {
            MovieAppBar(backButtonClick = onNavigateBack, showBackButton = true)
        },
        modifier = modifier
    ) {
        ProgressIndicator(
            progress = state.matchProgress,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            AnimatedContent(
                targetState = state.currentQuestionType,
                transitionSpec = {
                    (fadeIn(animationSpec = tween(200, delayMillis = 90)) +
                            slideIn(tween(400)) { size -> IntOffset(0, -10) })
                        .togetherWith(
                            fadeOut(animationSpec = tween(400))
                                    + slideOut(
                                tween(500)
                            ) { size -> IntOffset(0, 10) }
                        )
                }) { currentQuestionType ->
                Column(
                    modifier = Modifier.verticalScroll(
                        rememberScrollState(),
                        reverseScrolling = true
                    ),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    val moodQuestions =
                        state.moodQuestions.takeIf { currentQuestionType == QuestionType.MOOD }
                            ?: state.selectedMoodQuestions
                    val genreQuestions =
                        state.genreQuestions.takeIf { currentQuestionType == QuestionType.GENRE }
                            ?: state.selectedGenres
                    val timeQuestion =
                        state.timeQuestions.takeIf { currentQuestionType == QuestionType.TIME }
                            ?: state.selectedTimeQuestion
                    val movieTypeQuestions =
                        state.movieTypeQuestions.takeIf { currentQuestionType == QuestionType.TYPE }
                            ?: state.selectedMovieTypeQuestion

                    QuestionAndAnswersLayout(
                        questionType = QuestionType.MOOD,
                        questions = moodQuestions,
                        onAnswerSelected =
                            if (currentQuestionType.ordinal == QuestionType.MOOD.ordinal)
                                onAnswerSelected
                            else { _, _ -> },
                        modifier = Modifier.alpha(.3f.takeIf { currentQuestionType != QuestionType.MOOD }
                            ?: 1f)
                    )
                    if (currentQuestionType.ordinal >= QuestionType.GENRE.ordinal)
                        QuestionAndAnswersLayout(
                            questionType = QuestionType.GENRE,
                            questions = genreQuestions,
                            onAnswerSelected = if (currentQuestionType.ordinal == QuestionType.GENRE.ordinal)
                                onAnswerSelected
                            else { _, _ -> },
                            modifier = Modifier.alpha(.3f.takeIf { currentQuestionType != QuestionType.GENRE }
                                ?: 1f)
                        )
                    if (currentQuestionType.ordinal >= QuestionType.TIME.ordinal)
                        QuestionAndAnswersLayout(
                            questionType = QuestionType.TIME,
                            questions = timeQuestion,
                            onAnswerSelected = if (currentQuestionType.ordinal == QuestionType.TIME.ordinal)
                                onAnswerSelected
                            else { _, _ -> },
                            modifier = Modifier.alpha(.3f.takeIf { currentQuestionType != QuestionType.TIME }
                                ?: 1f)
                        )
                    if (currentQuestionType.ordinal >= QuestionType.TYPE.ordinal)
                        QuestionAndAnswersLayout(
                            questionType = QuestionType.TYPE,
                            questions = movieTypeQuestions,
                            onAnswerSelected = if (currentQuestionType.ordinal == QuestionType.TYPE.ordinal)
                                onAnswerSelected
                            else { _, _ -> },
                            modifier = Modifier.alpha(.3f.takeIf { currentQuestionType != QuestionType.TYPE || state.isLoadingRecommendations }
                                ?: 1f)
                        )

                    if (state.isLoadingRecommendations)
                        MovieText(
                            text = stringResource(R.string.loading_recommendations),
                            style = Theme.textStyle.body.medium.medium,
                            color = Theme.colors.shade.primary,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )

                }
            }

            MovieButton(
                buttonText = stringResource(if (state.currentQuestionType == QuestionType.TYPE) R.string.start_matching else R.string.next),
                textColor = nextButtonTextColor,
                textStyle = Theme.textStyle.body.medium.medium,
                onClick = onClickNextQuestion,
                buttonColor = nextButtonColor,
                isLoading = state.isLoadingRecommendations,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
        }
    }

}

