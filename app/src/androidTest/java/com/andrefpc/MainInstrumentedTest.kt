package com.andrefpc

import android.view.Gravity
import android.widget.TextView
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andrefpc.instrumented_mocks.MockPosts
import com.andrefpc.instrumented_mocks.MockRedditRepositoryImpl
import com.andrefpc.instrumented_mocks.Modules
import com.andrefpc.ui.MainActivity
import com.andrefpc.ui.PostsAdapter.PostsViewHolder
import com.andrefpc.util.AnimationSingleton
import com.andrefpc.util.ViewActions.clickChildViewWithId
import com.andrefpc.util.ViewActions.withCustomConstraints
import com.andrefpc.util.ViewAssertions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class MainInstrumentedTest {
    private val testDispatcher = AndroidTestContextProvider()
    private val redditRepository = MockRedditRepositoryImpl()

    private val modules = Modules(
        dispatcher = testDispatcher,
        redditRepository = redditRepository
    )

    @Before
    fun setUpKoinModules() {
        AnimationSingleton.animationEnabled = false
        loadKoinModules(modules.getList())
    }

    /**
     * Test to verify if the loaded list items have the correct data (from the mock)
     */
    @Test
    fun verify_posts() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Image"))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(1, scrollTo()))
        posts.check(ViewAssertions.atPositionWithText(1, postAuthor, "Test Video"))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(2, scrollTo()))
        posts.check(ViewAssertions.atPositionWithText(2, postAuthor, "Test Embed"))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(3, scrollTo()))
        posts.check(ViewAssertions.atPositionWithText(3, postAuthor, "Test Link"))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(4, scrollTo()))
        posts.check(ViewAssertions.atPositionWithText(4, postAuthor, "Test Without Thumb"))
    }

    /**
     * Test to click on a image post and verify if the post was shown correctly in main layout
     */
    @Test
    fun click_on_image_post() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(0, scrollTo()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(0, click()))
        toolbar.check(matches(withText("Test Image")))
        contentTitle.check(matches(withText("Test Image")))
        contentSubreddit.check(matches(withText("Posted by Test Image")))
        contentImage.check(matches(isDisplayed()))
    }

    /**
     * Test to click on a video post and verify if the post was shown correctly in main layout
     */
    @Test
    fun click_on_video_post() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(1, scrollTo()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(1, click()))
        toolbar.check(matches(withText("Test Video")))
        contentTitle.check(matches(withText("Test Video")))
        contentSubreddit.check(matches(withText("Posted by Test Video")))
        contentVideo.check(matches(isDisplayed()))
    }

    /**
     * Test to click on a embed video/gif post
     * and verify if the post was shown correctly in main layout
     */
    @Test
    fun click_on_embed_post() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(2, scrollTo()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(2, click()))
        toolbar.check(matches(withText("Test Embed")))
        contentTitle.check(matches(withText("Test Embed")))
        contentSubreddit.check(matches(withText("Posted by Test Embed")))
        contentEmbed.check(matches(isDisplayed()))
    }

    /**
     * Test to click on a link post and verify if the post was shown correctly in main layout
     */
    @Test
    fun click_on_link_post() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(3, scrollTo()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(3, click()))
        toolbar.check(matches(withText("Test Link")))
        contentTitle.check(matches(withText("Test Link")))
        contentSubreddit.check(matches(withText("Posted by Test Link")))
        contentLink.check(matches(isDisplayed()))
        contentLink.check(matches(withText("https://www.reddit.com/")))
    }

    /**
     * Test to click on a post that don't have a thumb image
     * and verify if the post was shown correctly in main layout
     */
    @Test
    fun click_on_without_thumb_post() = testDispatcher.testCoroutineDispatcher.runBlockingTest {
        redditRepository.setSuccessResponse(MockPosts.getPosts())
        launchActivity<MainActivity>()
        posts.check(matches(isDisplayed()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(4, scrollTo()))
        posts.perform(actionOnItemAtPosition<PostsViewHolder>(4, click()))
        toolbar.check(matches(withText("Test Without Thumb")))
        contentTitle.check(matches(withText("Test Without Thumb")))
        contentSubreddit.check(matches(withText("Posted by Test Without Thumb")))
        contentLink.check(matches(isDisplayed()))
        contentLink.check(matches(withText("https://www.reddit.com/")))
    }

    /**
     * Test to click on a RecyclerView item and verify if
     * the read bullet is hidden after open the list again
     */
    @Test
    fun click_on_item_and_verify_read_status() =
        testDispatcher.testCoroutineDispatcher.runBlockingTest {
            redditRepository.setSuccessResponse(MockPosts.getPosts())
            launchActivity<MainActivity>()
            posts.check(matches(isDisplayed()))
            posts.check(ViewAssertions.atPositionDisplayed(0, postRead, true))
            posts.perform(actionOnItemAtPosition<PostsViewHolder>(0, scrollTo()))
            posts.perform(actionOnItemAtPosition<PostsViewHolder>(0, click()))
            onView(withId(R.id.drawer_layout)).check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open())
            posts.check(ViewAssertions.atPositionDisplayed(0, postRead, false))
        }

    /**
     * Test to remove an item of the RecyclerView
     * and verify if the item was removed
     */
    @Test
    fun remove_on_item_and_verify_if_was_removed() =
        testDispatcher.testCoroutineDispatcher.runBlockingTest {
            redditRepository.setSuccessResponse(MockPosts.getPosts())
            launchActivity<MainActivity>()
            posts.check(matches(isDisplayed()))
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Image"))
            posts.perform(
                actionOnItemAtPosition<PostsViewHolder>(
                    0,
                    clickChildViewWithId(postClear)
                )
            )
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Video"))
        }

    /**
     * Test to click on the clear and reload button
     * and verify if list was reloaded
     */
    @Test
    fun click_on_clear_and_reload_button() =
        testDispatcher.testCoroutineDispatcher.runBlockingTest {
            redditRepository.setSuccessResponse(MockPosts.getPosts())
            launchActivity<MainActivity>()
            posts.check(matches(isDisplayed()))
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Image"))
            redditRepository.setSuccessResponse(MockPosts.getPosts().reversed())
            clearButton.perform(click())
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Without Thumb"))
        }


    /**
     * Test to swipe to refresh action
     * and verify if list was reloaded
     */
    @Test
    fun swipe_to_refresh() =
        testDispatcher.testCoroutineDispatcher.runBlockingTest {
            redditRepository.setSuccessResponse(MockPosts.getPosts())
            launchActivity<MainActivity>()
            posts.check(matches(isDisplayed()))
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Image"))
            redditRepository.setSuccessResponse(MockPosts.getPosts().reversed())
            swipeRefresh.perform(withCustomConstraints(swipeDown(), isDisplayingAtLeast(85)))
            posts.check(ViewAssertions.atPositionWithText(0, postAuthor, "Test Without Thumb"))
        }


    // RecyclerView Item View Ids
    private val postAuthor = R.id.post_author
    private val postRead = R.id.post_read
    private val postClear = R.id.post_clear


    // View Interactions
    private val posts = onView(withId(R.id.posts_list))
    private val toolbar =
        onView(allOf(instanceOf(TextView::class.java), withParent(withId(R.id.toolbar))))
    private val contentSubreddit = onView(withId(R.id.content_subreddit))
    private val contentTitle = onView(withId(R.id.content_title))
    private val contentImage = onView(withId(R.id.content_image))
    private val contentVideo = onView(withId(R.id.content_video))
    private val contentEmbed = onView(withId(R.id.content_embed))
    private val contentLink = onView(withId(R.id.content_link))
    private val clearButton = onView(withId(R.id.clear_button))
    private val swipeRefresh = onView(withId(R.id.swipe_refresh))

    @After
    fun tearDown() {
        unloadKoinModules(modules.getList())
    }
}