package com.andrefpc.di

import com.andrefpc.api.RedditApi
import com.andrefpc.repository.RedditRepository
import com.andrefpc.repository.RedditRepositoryImpl
import com.andrefpc.ui.MainViewModel
import com.andrefpc.util.CoroutineContextProvider
import com.andrefpc.util.image.ImageUtil
import com.andrefpc.util.image.ImageUtilImpl
import org.koin.androidx.viewmodel.dsl.viewModel
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
    viewModel {
        MainViewModel(
            dispatchers = get(),
            redditRepository = get(),
            imageUtil = get()
        )
    }
}

val remoteModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(okHttpClient = get()) }
}

val coroutineContextProviderModule = module {
    single { CoroutineContextProvider() }
}

val utilModule = module {
    factory<ImageUtil> {
        ImageUtilImpl(context = get())
    }
}
