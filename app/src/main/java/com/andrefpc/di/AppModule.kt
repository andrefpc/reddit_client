package com.andrefpc.di

import com.andrefpc.util.CoroutineContextProvider
import org.koin.dsl.module

val apiModule = module {

}

val repositoryModule = module {

}

val viewModelModule = module {

}

val remoteModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
}

val coroutineContextProviderModule = module {
    single { CoroutineContextProvider() }
}
