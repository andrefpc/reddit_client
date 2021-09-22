package com.andrefpc

import android.app.Application
import com.andrefpc.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class InitApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@InitApplication)
            modules(
                listOf(
                    apiModule,
                    repositoryModule,
                    viewModelModule,
                    remoteModule,
                    coroutineContextProviderModule
                )
            )
        }
    }
}