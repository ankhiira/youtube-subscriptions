package com.gabchmel.youtubesubscriptions.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.data.SignInResult
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.profile.presentation.model.ProfileEvent
import com.gabchmel.youtubesubscriptions.profile.presentation.model.ProfileNavEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    val user = authRepository.user

    private val _signInResult = MutableSharedFlow<SignInResult>()
    val signInResult = _signInResult.asSharedFlow()

    private val _navigationEvent = Channel<ProfileNavEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SignOut -> {
                signOut()
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()

            _navigationEvent.send(ProfileNavEvent.NavigateToSignIn)
        }
    }
}