package com.example.projectpiggy2.application

import android.app.Application
import com.example.projectpiggy2.di.modules.userModule
import com.example.projectpiggy2.di.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PocketPiggy: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PocketPiggy)
            modules(listOf(
                userModule,
                viewModelModule
            ))

        }
    }
}