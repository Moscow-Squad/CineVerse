package com.moscow.cineverse.screen.match

interface MatchInteractionListener {
    fun onClickStartMatching()
    fun onClickFinishMatching()
    fun onClickNextQuestion()
    fun onAnswerSelected(type: QuestionType, answer: QuestionUiState)
    fun onNavigateBack()
    fun onMovieClick(id: Int)
    fun onSaveClick(id: Int)
    fun onPlayClick(id: Int, url: String)
    fun onRetry()
}