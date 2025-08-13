package com.moscow.cineverse.screen.match

import com.moscow.domain.model.MediaType

sealed class MatchEvent {
    data object OnClickStartMatching : MatchEvent()
    data object OnClickFinishMatching : MatchEvent()
    data class OnMediaItemClick(val id: Int, val type: MediaType): MatchEvent()
}
