package com.gabchmel.youtubesubscriptions.di

import androidx.credentials.CredentialManager
import com.gabchmel.youtubesubscriptions.auth.GoogleAuthProvider
import com.gabchmel.youtubesubscriptions.subsList.data.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subsList.presentation.SubsListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    singleOf(::SubscriptionRepository)
    viewModelOf(::SubsListViewModel)

    factory<CredentialManager> { CredentialManager.create(androidContext()) }
    factoryOf(::GoogleAuthProvider) bind GoogleAuthProvider::class
}