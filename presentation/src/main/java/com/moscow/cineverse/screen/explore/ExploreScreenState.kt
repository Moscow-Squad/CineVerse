package com.moscow.cineverse.screen.explore

data class ExploreScreenState(
    val searchKeyWord: String = "",

    val searchResult: Map<String, List<MediaItemUi>> = mutableMapOf(),
    val actorsSearchResult: List<ActorUi> = emptyList(),

    val isLoading: Boolean = false,
    val selectedTab: String? = null,
    val remoteSuggestions:List<String> = emptyList(),

    val isSearchBarClickedOn : Boolean = false,
    val showHistory : Boolean = false,
    val showSuggestions : Boolean = false,
    val localSuggestions: List<SuggestItemUiState> = listOf(),

    val moviesGenres: List<GenreUi> = emptyList(),
    val seriesGenres: List<GenreUi> = emptyList(),

){
    data class MediaItemUi(
        val id: Int,
        val name: String,
        val posterPath: String,
        val rating: Float,
        val genres: List<String>,
        val releaseDate: String,
    )
    data class ActorUi(
        val title: String,
        val icon: String,
        val id: Int
    )

    data class GenreUi(
        val id: Int,
        val name: String
    )
}
