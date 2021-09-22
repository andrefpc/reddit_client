package com.andrefpc.di

import com.andrefpc.api.RedditApi
import com.andrefpc.repository.RedditRepository
import com.andrefpc.repository.RedditRepositoryImpl
import com.andrefpc.util.CoroutineContextProvider
import org.koin.dsl.module

val apiModule = module {
    single { createApi<RedditApi>(retrofit = get()) }
}

val repositoryModule = module {
    single<RedditRepository> {
        RedditRepositoryImpl(redditApi = get())
    }
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
