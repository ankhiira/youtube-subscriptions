package com.gabchmel.youtubesubscriptions.auth.di

import androidx.credentials.CredentialManager
import com.gabchmel.youtubesubscriptions.auth.data.AuthRepositoryImpl
import com.gabchmel.youtubesubscriptions.auth.data.data_source.UserSessionDataSource
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.ObserveAuthStateUseCase
import com.gabchmel.youtubesubscriptions.auth.domain.use_cases.SignOutUseCase
import com.gabchmel.youtubesubscriptions.auth.presentation.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::UserSessionDataSource)
    factory<CredentialManager> { CredentialManager.create(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(androidContext(), get(), get()) }

    singleOf(::ObserveAuthStateUseCase)
    singleOf(::SignOutUseCase)

    viewModelOf(::SignInViewModel)
}