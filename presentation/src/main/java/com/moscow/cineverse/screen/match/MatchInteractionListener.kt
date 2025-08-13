package com.moscow.cineverse.screen.match

import com.moscow.domain.model.MediaType

interface MatchInteractionListener {
    fun onClickStartMatching()
    fun onClickFinishMatching()
    fun onClickNextQuestion()
    fun onAnswerSelected(questionIndex: Int, answer: String)
    fun onNavigateBack()
    fun onMediaItemClick(id: Int, type: MediaType)
}