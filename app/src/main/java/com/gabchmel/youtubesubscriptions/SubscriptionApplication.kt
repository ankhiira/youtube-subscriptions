package com.gabchmel.youtubesubscriptions

import android.app.Application
import com.gabchmel.youtubesubscriptions.di.appModule
import com.gabchmel.youtubesubscriptions.di.viewModelModule
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
                viewModelModule
            )
        }
    }
}