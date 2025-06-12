package com.gabchmel.youtubesubscriptions.auth.presentation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.data.AuthRepository
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    val user = authRepository.user

    init {
        viewModelScope.launch {
            authRepository.silentSignIn()
        }
    }

    fun getSignInIntent(): Intent {
        return authRepository.getSignInIntent()
    }

    fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModelScope.launch {
                val signInResult = authRepository.handleSignInResult(result.data)
                if (!signInResult.isSuccess) {
                    Log.w("AuthViewModel", "Sign-in failed: ${signInResult.errorMessage}")
                }
            }
        } else {
            Log.w("AuthViewModel", "Sign-in flow was cancelled or failed.")
        }
    }
}