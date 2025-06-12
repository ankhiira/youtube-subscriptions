package com.gabchmel.youtubesubscriptions.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.data.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.data.SignInResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    val user = authRepository.user

    private val _signInResult = MutableSharedFlow<SignInResult>()
    val signInResult = _signInResult.asSharedFlow()

//    fun getSignInIntent(): Intent {
//        return authRepository.getSignInIntent()
//    }

//    fun onSignInResult(result: ActivityResult) {
//        result.data?.let { intent ->
//            viewModelScope.launch {
//                val signInResult = authRepository.handleSignInResult(intent)
//                _signInResult.emit(signInResult)
//            }
//        }
//    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}