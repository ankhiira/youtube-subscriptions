package com.gabchmel.youtubesubscriptions.core.data

import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit

class TokenProvider(private val securePrefs: SharedPreferences) {

    fun saveToken(accessToken: String) {
        securePrefs.edit { putString(KEY_ACCESS_TOKEN, accessToken) }
    }

    fun getToken(): String? {
        return securePrefs.getString(KEY_ACCESS_TOKEN, null)
    }

    fun clearToken() {
        securePrefs.edit { remove(KEY_ACCESS_TOKEN) }
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
    }
}