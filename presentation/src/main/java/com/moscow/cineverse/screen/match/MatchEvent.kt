package com.moscow.cineverse.screen.match

sealed class MatchEvent {
    data object OnClickStartMatching : MatchEvent()
    data object OnClickFinishMatching : MatchEvent()
}
