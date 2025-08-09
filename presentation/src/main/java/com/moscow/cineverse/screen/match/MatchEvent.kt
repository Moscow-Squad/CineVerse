package com.moscow.cineverse.screen.match

sealed class MatchEvent {
    data object OnClickStartMatching : MatchEvent()
    data object OnClickNextPage : MatchEvent()
    data object OnClickPreviousPage : MatchEvent()
    data object OnClickFinishMatching : MatchEvent()
    data class OnClickAnswer(val answer: List<Int>) : MatchEvent()
}
