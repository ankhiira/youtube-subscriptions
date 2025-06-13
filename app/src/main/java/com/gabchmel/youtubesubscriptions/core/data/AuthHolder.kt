package com.gabchmel.youtubesubscriptions.core.data

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object AuthHolder {
    private val _account = MutableStateFlow<GoogleSignInAccount?>(null)
    val account = _account.asStateFlow()

    fun setAccount(account: GoogleSignInAccount?) {
        _account.value = account
    }
}