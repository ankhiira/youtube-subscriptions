package com.gabchmel.youtubesubscriptions.core.di

import com.gabchmel.youtubesubscriptions.core.data.TokenProvider
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::TokenProvider)
}