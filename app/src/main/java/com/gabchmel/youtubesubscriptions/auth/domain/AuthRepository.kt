package com.gabchmel.youtubesubscriptions.auth.domain

import android.content.Intent
import com.gabchmel.youtubesubscriptions.auth.data.SignInResult
import com.gabchmel.youtubesubscriptions.core.domain.UserProfile
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val user: StateFlow<UserProfile?>

    suspend fun automaticSignIn()
    fun getSignInIntent(): Intent
    suspend fun handleSignInResult(intent: Intent?): SignInResult
    suspend fun signOut()
}