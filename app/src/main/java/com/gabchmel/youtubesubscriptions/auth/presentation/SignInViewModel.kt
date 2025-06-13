package com.gabchmel.youtubesubscriptions.auth.presentation

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.ObserveAuthStateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(
    observeAuthStateUseCase: ObserveAuthStateUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignInState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                authRepository.automaticSignIn()

                observeAuthStateUseCase()
                    .collect { userProfile ->
                        _uiState.update {
                            it.copy(
                                userProfile = userProfile,
                                isLoading = false // Loading is now definitely false
                            )
                        }
                    }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false, userProfile = null) }
            }
        }
    }

    fun getSignInIntent(): Intent {
        return authRepository.getSignInIntent()
    }

    fun handleSignInResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true) }
                try {
                    val signInResult = authRepository.handleSignInResult(result.data)
                    if (!signInResult.isSuccess) {
                        _uiState.update { it.copy(isLoading = false) }
                    }
                } catch (_: Exception) {
                    _uiState.update { it.copy(isLoading = false) }
                }
            }
        } else {
            Log.w("AuthViewModel", "Sign-in flow was cancelled or failed.")
        }
    }
}