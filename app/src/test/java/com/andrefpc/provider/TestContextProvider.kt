package com.andrefpc.provider

import com.andrefpc.util.CoroutineContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider() {
    val testCoroutineDispatcher = TestCoroutineDispatcher()

    override val Main: CoroutineContext = testCoroutineDispatcher
    override val IO: CoroutineContext = testCoroutineDispatcher
}
