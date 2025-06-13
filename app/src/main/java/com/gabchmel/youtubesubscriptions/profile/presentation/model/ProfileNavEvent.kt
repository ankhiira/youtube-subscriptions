package com.gabchmel.youtubesubscriptions.profile.presentation.model

sealed class ProfileNavEvent {
    object NavigateToSignIn : ProfileNavEvent()
}