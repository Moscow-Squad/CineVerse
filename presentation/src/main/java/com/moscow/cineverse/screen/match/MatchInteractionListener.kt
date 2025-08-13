package com.moscow.cineverse.screen.match

interface MatchInteractionListener {
    fun onClickStartMatching()
    fun onClickFinishMatching()
    fun onClickNextQuestion()
    fun onAnswerSelected(questionIndex: Int, answer: String)
    fun onNavigateBack()
    fun onMovieClick(id: Int)
    fun onSaveClick(id: Int)
    fun onPlayClick(url: String)
}