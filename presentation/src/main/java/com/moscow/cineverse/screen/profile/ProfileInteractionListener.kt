package com.moscow.cineverse.screen.profile

interface ProfileInteractionListener {
    fun onShowEditProfileBottomSheet()
    fun onShowLogoutBottomSheet()
    fun onShowLanguageBottomSheet()
    fun onShowPreferencesBottomSheet()
    fun onClickEditProfile()
    fun onClickLogout()
    fun onClickLogin()
    fun onSelectedPreference(enable: Boolean)
    fun onExitWebView()
    fun onSelectedLanguage(language:String)
    fun onCancelLanguageBottomSheet()
    fun onCancelEditProfileBottomSheet()
    fun onCancelLogoutBottomSheet()
    fun onCancelPreferencesBottomSheet()
    fun onClickMyRatings()
    fun onClickMyCollections()
    fun onClickHistory()
}