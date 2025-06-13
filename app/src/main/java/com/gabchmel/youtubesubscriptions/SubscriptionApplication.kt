package com.gabchmel.youtubesubscriptions

import android.app.Application
import com.gabchmel.youtubesubscriptions.auth.di.authModule
import com.gabchmel.youtubesubscriptions.core.di.appModule
import com.gabchmel.youtubesubscriptions.core.di.storageModule
import com.gabchmel.youtubesubscriptions.profile.di.profileModule
import com.gabchmel.youtubesubscriptions.subscription.di.subscriptionModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SubscriptionApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@SubscriptionApplication)
            modules(
                appModule,
                storageModule,
                authModule,
                subscriptionModule,
                profileModule
            )
        }
    }
}