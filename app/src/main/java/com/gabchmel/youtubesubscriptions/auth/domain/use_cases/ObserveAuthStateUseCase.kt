package com.gabchmel.youtubesubscriptions.auth.domain.use_cases

import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.core.domain.UserProfile
import kotlinx.coroutines.flow.Flow

class ObserveAuthStateUseCase(private val authRepository: AuthRepository) {
    operator fun invoke(): Flow<UserProfile?> = authRepository.user
}