package com.gabchmel.youtubesubscriptions.di

import com.gabchmel.youtubesubscriptions.auth.presentation.SignInViewModel
import com.gabchmel.youtubesubscriptions.profile.presentation.UserProfileViewModel
import com.gabchmel.youtubesubscriptions.subscriptionDetail.presentation.SubscriptionDetailViewModel
import com.gabchmel.youtubesubscriptions.subscriptionsList.presentation.SubscriptionsListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SubscriptionsListViewModel)
    viewModelOf(::SubscriptionDetailViewModel)
    viewModelOf(::UserProfileViewModel)
    viewModelOf(::SignInViewModel)
}