package com.gabchmel.youtubesubscriptions.di

import com.gabchmel.youtubesubscriptions.subsList.data.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subsList.presentation.SubsListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::SubscriptionRepository)
    viewModelOf(::SubsListViewModel)
}