package com.gabchmel.youtubesubscriptions.profile.presentation

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthHolder {
    private val _account = MutableStateFlow<GoogleSignInAccount?>(null)
    val account = _account.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    val accessToken = _accessToken.asStateFlow()

    fun setAccount(account: GoogleSignInAccount?) {
        _account.value = account
    }

    fun setToken(token: String?) {
        _accessToken.value = token
    }
}