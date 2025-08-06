package com.moscow.cineverse.screen.profile

interface ProfileInteractionListener {
    fun onShowEditProfileBottomSheet()
    fun onShowLogoutBottomSheet()
    fun onShowLanguageBottomSheet()
    fun onClickEditProfile()
    fun onClickLogout()
    fun onClickLogin()
    fun onExitWebView()
    fun onSelectedLanguage(language:String)
    fun onCancelLanguageBottomSheet()
    fun onCancelEditProfileBottomSheet()
    fun onCancelLogoutBottomSheet()
    fun onClickMyRatings()
    fun onClickMyCollections()
    fun onClickHistory()
}