package com.andrefpc.mocks

import com.andrefpc.repository.RedditRepository
import com.andrefpc.ui.MainViewModel
import com.andrefpc.util.CoroutineContextProvider
import com.andrefpc.util.image.ImageUtil
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class Modules(
    dispatcher: CoroutineContextProvider,
    redditRepository: RedditRepository? = null,
    imageUtil: ImageUtil? = null
) {
    private val viewModelModule = module {
        viewModel {
            MainViewModel(
                dispatchers = dispatcher,
                redditRepository = redditRepository ?: get(),
                imageUtil = imageUtil ?: get()
            )
        }
    }

    private val repositoryModule = module {
        single<RedditRepository> {
            MockRedditRepositoryImpl()
        }
    }

    private val utilModule = module {
        factory<ImageUtil> {
            MockImageUtilImpl()
        }
    }

    fun getList(): List<Module> {
        viewModelModule.makeOptions(override = true)
        return listOf(
            viewModelModule,
            repositoryModule,
            utilModule
        )
    }
}