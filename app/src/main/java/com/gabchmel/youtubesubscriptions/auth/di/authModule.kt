package com.gabchmel.youtubesubscriptions.auth.di

import androidx.credentials.CredentialManager
import com.gabchmel.youtubesubscriptions.auth.data.AuthRepositoryImpl
import com.gabchmel.youtubesubscriptions.auth.domain.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.presentation.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    factory<CredentialManager> { CredentialManager.create(androidContext()) }
    single<AuthRepository> { AuthRepositoryImpl(androidContext(), get()) }

    viewModelOf(::SignInViewModel)
}