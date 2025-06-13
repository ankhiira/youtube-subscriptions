package com.gabchmel.youtubesubscriptions.auth.domain.use_cases

import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository

class SignOutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.signOut()
}