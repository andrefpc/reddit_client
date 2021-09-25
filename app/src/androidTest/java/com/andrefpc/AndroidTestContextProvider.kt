package com.andrefpc

import com.andrefpc.util.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class AndroidTestContextProvider : CoroutineContextProvider() {
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    override val Main: CoroutineContext = testCoroutineDispatcher
    override val IO: CoroutineContext = testCoroutineDispatcher
}
