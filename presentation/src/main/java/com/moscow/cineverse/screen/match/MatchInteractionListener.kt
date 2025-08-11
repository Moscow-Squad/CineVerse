package com.moscow.cineverse.screen.match

interface MatchInteractionListener {
    fun onClickStartMatching()
    fun onClickFinishMatching()
    fun onClickNextQuestion()
    fun onAnswerSelected(questionIndex: Int, answer: String)
    fun onNavigateBack()
}