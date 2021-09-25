package com.andrefpc

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andrefpc.data.UIState
import com.andrefpc.mocks.MockPosts
import com.andrefpc.mocks.MockRedditRepositoryImpl
import com.andrefpc.mocks.Modules
import com.andrefpc.provider.TestContextProvider
import com.andrefpc.ui.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class MainUnitTest : KoinTest {
    private val testDispatcher = TestContextProvider()
    private val viewModel: MainViewModel by inject()
    private val redditRepository = MockRedditRepositoryImpl()

    private val modules = Modules(
        dispatcher = testDispatcher,
        redditRepository = redditRepository
    )

    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(Mockito.mock(Context::class.java))
        loadKoinModules(modules.getList())
    }

    /**
     * Test to get posts to refresh the list and verify if the UIState is correct
     */
    @Test
    fun `Get posts to refresh the list`() {
        val postList = MockPosts.getImagePost()
        redditRepository.setSuccessResponse(postList)
        assertNull(viewModel.uiState.value)
        testDispatcher.testCoroutineDispatcher.pauseDispatcher()
        viewModel.getPosts(refresh = true)
        assert(viewModel.uiState.value == UIState.Loading)
        testDispatcher.testCoroutineDispatcher.resumeDispatcher()

        assertNotNull(viewModel.uiState.value)
        assert(viewModel.uiState.value is UIState.RefreshList)
        assertEquals(
            (viewModel.uiState.value as UIState.RefreshList).children[0].data.name,
            postList[0].data.name
        )
    }

    /**
     * Test to get posts after lastItem to add to list and verify if the UIState is correct
     */
    @Test
    fun `Get posts after lastItem to add to list`() {
        val postList = MockPosts.getImagePost()
        redditRepository.setSuccessResponse(postList)
        assertNull(viewModel.uiState.value)
        testDispatcher.testCoroutineDispatcher.pauseDispatcher()
        viewModel.getPosts(lastItemName = "Test", refresh = false)
        assert(viewModel.uiState.value != UIState.Loading)
        testDispatcher.testCoroutineDispatcher.resumeDispatcher()

        assertNotNull(viewModel.uiState.value)
        assert(viewModel.uiState.value is UIState.AddList)
        assertEquals(
            (viewModel.uiState.value as UIState.AddList).children[0].data.name,
            postList[0].data.name
        )
    }


    /**
     * Test to get posts after lastItem to refresh the list and verify if the UIState is correct
     */
    @Test
    fun `Get posts after lastItem to refresh the list`() {
        val postList = MockPosts.getImagePost()
        redditRepository.setSuccessResponse(postList)
        assertNull(viewModel.uiState.value)
        testDispatcher.testCoroutineDispatcher.pauseDispatcher()
        viewModel.getPosts(lastItemName = "Test", refresh = true)
        assert(viewModel.uiState.value == UIState.Loading)
        testDispatcher.testCoroutineDispatcher.resumeDispatcher()

        assertNotNull(viewModel.uiState.value)
        assert(viewModel.uiState.value is UIState.RefreshList)
        assertEquals(
            (viewModel.uiState.value as UIState.RefreshList).children[0].data.name,
            postList[0].data.name
        )
    }

    /**
     * Test to get posts to refresh the list with error and verify if the UIState is correct
     */
    @Test
    fun `Get posts to refresh the list with error`() {
        redditRepository.setErrorResponse()
        assertNull(viewModel.uiState.value)
        testDispatcher.testCoroutineDispatcher.pauseDispatcher()
        viewModel.getPosts(refresh = true)
        assert(viewModel.uiState.value == UIState.Loading)
        testDispatcher.testCoroutineDispatcher.resumeDispatcher()

        assertNotNull(viewModel.uiState.value)
        assert(viewModel.uiState.value is UIState.Error)
    }

    /**
     * Test to get posts to add to list with error and verify if the UIState is correct
     */
    @Test
    fun `Get posts to add to list with error`() {
        redditRepository.setErrorResponse()
        assertNull(viewModel.uiState.value)
        testDispatcher.testCoroutineDispatcher.pauseDispatcher()
        viewModel.getPosts(lastItemName = "Test", refresh = false)
        assert(viewModel.uiState.value != UIState.Loading)
        testDispatcher.testCoroutineDispatcher.resumeDispatcher()

        assertNull(viewModel.uiState.value)
    }


    @After
    fun tearDown() {
        unloadKoinModules(modules.getList())
    }
}