package com.gabchmel.youtubesubscriptions.auth

import android.content.Context
import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.gabchmel.youtubesubscriptions.auth.model.GoogleAccount
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException

class GoogleAuthUiProvider(
    private val activityContext: Context,
    private val credentialManager: CredentialManager
) {
    suspend fun signIn(): GoogleAccount? = try {
        val credential = credentialManager.getCredential(
            context = activityContext,
            request = getCredentialRequest()
        ).credential
        handleSignIn(credential)
    } catch (e: GetCredentialException) {
        println(
            e
        )
        null
    }

    private fun handleSignIn(credential: Credential): GoogleAccount? = when {
        credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL -> {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                GoogleAccount(
                    token = googleIdTokenCredential.idToken,
                    displayName = googleIdTokenCredential.displayName ?: "",
                    profileImageUrl = googleIdTokenCredential.profilePictureUri?.toString()
                )
            } catch (e: GoogleIdTokenParsingException) {
                null
            }
        }
        else -> {
            Log.d("Auth", "Unexpected type of credential")
            null
        }
    }

    private fun getCredentialRequest(): GetCredentialRequest = GetCredentialRequest.Builder()
        .addCredentialOption(getGoogleIdOption())
        .build()

    private fun getGoogleIdOption(): GetGoogleIdOption = GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(false)
        .setAutoSelectEnabled(true)
        .setServerClientId(WEB_CLIENT_ID)
        .build()

    companion object {
        const val WEB_CLIENT_ID = "212234216487-6ug97g2bjssfpss9s0boartfd280gge7.apps.googleusercontent.com"
    }
}