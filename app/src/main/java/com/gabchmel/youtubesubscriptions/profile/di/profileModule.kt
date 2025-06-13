package com.gabchmel.youtubesubscriptions.profile.di

import com.gabchmel.youtubesubscriptions.profile.presentation.ProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)
}