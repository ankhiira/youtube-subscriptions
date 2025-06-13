package com.gabchmel.youtubesubscriptions.auth.data.data_source

import com.gabchmel.youtubesubscriptions.core.domain.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSessionDataSource {

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile = _userProfile.asStateFlow()

    fun updateUser(userProfile: UserProfile?) {
        _userProfile.value = userProfile
    }

    fun clearSession() {
        _userProfile.value = null
    }
}