package com.gabchmel.youtubesubscriptions.auth.data

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.gabchmel.youtubesubscriptions.auth.model.UserProfile
import com.gabchmel.youtubesubscriptions.profile.presentation.AuthHolder
import com.google.android.gms.auth.GoogleAuthUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(private val context: Context) : AuthRepository {

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            // Request the scope for YouTube read-only access.
            // This is necessary to get an access token for the YouTube API.
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube.readonly"))
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    private val _user = MutableStateFlow<UserProfile?>(null)
    override val user = _user.asStateFlow()

    private val _accessToken = MutableStateFlow<String?>(null)
    override val accessToken = _accessToken.asStateFlow()

    override suspend fun silentSignIn() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
            // A user was previously signed in. We can try to refresh the token.
            handleSuccessfulSignIn(account)
        }
    }

    override fun getSignInIntent(): Intent {
        return googleSignInClient.signInIntent
    }

    override suspend fun handleSignInResult(intent: Intent?): SignInResult {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            val account = task.getResult(ApiException::class.java)
            handleSuccessfulSignIn(account)
        } catch (e: ApiException) {
            // Sign-in failed. Clear any existing state.
            clearAuthState()
            SignInResult(
                user = null,
                errorMessage = "Google Sign-In failed with code: ${e.statusCode}"
            )
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            googleSignInClient.signOut()
            clearAuthState()
        }
    }

    private suspend fun handleSuccessfulSignIn(account: GoogleSignInAccount): SignInResult {
        return withContext(Dispatchers.IO) {
            try {
                val token = account.account?.let {
                    GoogleAuthUtil.getToken(
                        context,
                        it,
                        "oauth2:https://www.googleapis.com/auth/youtube.readonly"
                    )
                }

                AuthHolder.setAccount(account)
                AuthHolder.setToken(token)

                _accessToken.value = token
                _user.value = account.toUserProfile()

                SignInResult(user = _user.value, errorMessage = null)
            } catch (e: Exception) {
                clearAuthState()
                SignInResult(user = null, errorMessage = "Failed to retrieve access token: ${e.message}")
            }
        }
    }

    private fun clearAuthState() {
        _user.value = null
        _accessToken.value = null
    }

    private fun GoogleSignInAccount.toUserProfile(): UserProfile {
        return UserProfile(
            name = this.displayName ?: "",
            photoUrl = this.photoUrl.toString(),
            email = this.email ?: ""
        )
    }
}

data class SignInResult(
    val user: UserProfile?,
    val errorMessage: String?
) {
    val isSuccess: Boolean get() = user != null
}