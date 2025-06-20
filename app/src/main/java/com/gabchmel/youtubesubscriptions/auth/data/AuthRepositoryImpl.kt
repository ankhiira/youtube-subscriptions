package com.gabchmel.youtubesubscriptions.auth.data

import android.content.Context
import android.content.Intent
import com.gabchmel.youtubesubscriptions.auth.data.data_source.UserSessionDataSource
import com.gabchmel.youtubesubscriptions.auth.data.mapper.toUserProfile
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.core.data.TokenProvider
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val context: Context,
    private val tokenProvider: TokenProvider,
    private val userSessionDataSource: UserSessionDataSource
) : AuthRepository {

    private val googleSignInClient: GoogleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope(YOUTUBE_SCOPE))
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    override val user = userSessionDataSource.userProfile

    override suspend fun automaticSignIn() {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        if (account != null) {
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
                        "oauth2:$YOUTUBE_SCOPE"
                    )
                }

                if (token != null) {
                    tokenProvider.saveToken(token)
                }

                val userProfile = account.toUserProfile()
                userSessionDataSource.updateUser(userProfile)

                SignInResult(user = userProfile, errorMessage = null)
            } catch (e: Exception) {
                clearAuthState()
                SignInResult(
                    user = null,
                    errorMessage = "Failed to retrieve access token: ${e.message}"
                )
            }
        }
    }

    private fun clearAuthState() {
        userSessionDataSource.clearSession()
        tokenProvider.clearToken()
    }

    companion object {
        private const val YOUTUBE_SCOPE = "https://www.googleapis.com/auth/youtube.readonly"
    }
}