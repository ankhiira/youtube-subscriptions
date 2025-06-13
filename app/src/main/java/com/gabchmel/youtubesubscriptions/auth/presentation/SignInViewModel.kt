package com.gabchmel.youtubesubscriptions.auth.presentation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.ObserveAuthStateUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SignInViewModel(
    observeAuthStateUseCase: ObserveAuthStateUseCase,
    private val authRepository: AuthRepository
): ViewModel() {

    val userProfile = observeAuthStateUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        viewModelScope.launch {
            authRepository.automaticSignIn()
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