package com.gabchmel.youtubesubscriptions.auth.data.mapper

import com.gabchmel.youtubesubscriptions.core.domain.UserProfile
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

fun GoogleSignInAccount.toUserProfile(): UserProfile {
    return UserProfile(
        name = this.displayName ?: "",
        photoUrl = this.photoUrl.toString(),
        email = this.email ?: ""
    )
}