package com.gabchmel.youtubesubscriptions.di

import androidx.credentials.CredentialManager
import com.gabchmel.youtubesubscriptions.auth.AuthRepository
import com.gabchmel.youtubesubscriptions.auth.AuthRepositoryImpl
import com.gabchmel.youtubesubscriptions.auth.GoogleAuthProvider
import com.gabchmel.youtubesubscriptions.subscriptionsList.data.YoutubeApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    factory<CredentialManager> { CredentialManager.create(androidContext()) }
    factoryOf(::GoogleAuthProvider) bind GoogleAuthProvider::class

    single<YoutubeApi> {
        Ktorfit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .httpClient(HttpClient { // Configure the Ktor client
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true // The YouTube API sends a lot of fields we don't need
                    })
                }
            })
            .build()
            .create()
    }

    single<AuthRepository> { AuthRepositoryImpl(androidContext()) }

//    singleOf(::YoutubeRepository)
}