package com.gabchmel.youtubesubscriptions.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.ObserveAuthStateUseCase
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.SignOutUseCase
import com.gabchmel.youtubesubscriptions.profile.presentation.model.ProfileEvent
import com.gabchmel.youtubesubscriptions.profile.presentation.model.ProfileNavEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel(
    observeAuthStateUseCase: ObserveAuthStateUseCase,
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    val userProfile = observeAuthStateUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

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
            signOutUseCase()
            _navigationEvent.send(ProfileNavEvent.NavigateToSignIn)
        }
    }
}