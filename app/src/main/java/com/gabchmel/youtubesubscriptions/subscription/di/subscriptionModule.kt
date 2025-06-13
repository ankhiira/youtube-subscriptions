package com.gabchmel.youtubesubscriptions.subscription.di

import com.gabchmel.youtubesubscriptions.subscription.data.SubscriptionRepositoryImpl
import com.gabchmel.youtubesubscriptions.subscription.data.YoutubeApi
import com.gabchmel.youtubesubscriptions.subscription.domain.SubscriptionRepository
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_detail.SubscriptionDetailViewModel
import com.gabchmel.youtubesubscriptions.subscription.presentation.subscription_list.SubscriptionListViewModel
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val subscriptionModule = module {
    single<YoutubeApi> {
        Ktorfit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .httpClient(HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            })
            .build()
            .create()
    }
    single<SubscriptionRepository> { SubscriptionRepositoryImpl(get(), get()) }

    viewModelOf(::SubscriptionListViewModel)
    viewModelOf(::SubscriptionDetailViewModel)
}