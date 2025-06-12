package com.gabchmel.youtubesubscriptions.auth.data

import android.content.Intent
import com.gabchmel.youtubesubscriptions.auth.model.UserProfile
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val user: StateFlow<UserProfile?>
    val accessToken: StateFlow<String?>

    suspend fun silentSignIn()
    fun getSignInIntent(): Intent
    suspend fun handleSignInResult(intent: Intent?): SignInResult
    suspend fun signOut()
}