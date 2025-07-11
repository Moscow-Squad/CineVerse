package com.moscow.cineverse.screen.explore

import com.moscow.cineverse.base.BaseViewModel

class ExploreViewModel :
    BaseViewModel<ExploreScreenState, ExploreScreenEvents>(ExploreScreenState()),
    ExploreInteractionListener {
}