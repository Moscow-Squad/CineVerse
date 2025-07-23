package com.moscow.cineverse.screen.login

interface LoginInteractionListener {
    fun onUsernameValueChanged(username: String)
    fun onPasswordValueChanged(password: String)
    fun onClickLogin()
    fun onClickJoinAsGuest()
    fun onClickCreateNewAccount()
    fun onDismissOrCancelSignUpBottomSheet()
    fun onClickGoToWebsite()
    fun onExitSignupBrowser()
}