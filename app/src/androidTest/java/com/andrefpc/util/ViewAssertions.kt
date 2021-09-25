package com.andrefpc.util

import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.core.IsNot

object ViewAssertions {
    /**
     * Assertion to check if the item in the RecyclerView position has a text
     * @param position position of the RecyclerView
     * @param layoutId The resource id of the item to be checked
     * @param text The text to be checked
     */
    fun atPositionWithText(position: Int, layoutId: Int, text: String): ViewAssertion {
        return matches(
            Matchers.atPosition(
                position,
                layoutId,
                ViewMatchers.withText(text)
            )
        )
    }

    /**
     * Assertion to check if the item in the RecyclerView is displayed
     * @param position position of the RecyclerView
     * @param layoutId The resource id of the item to be checked
     * @param isVisible The visibility boolean to be tested.
     */
    fun atPositionDisplayed(position: Int, layoutId: Int, isVisible: Boolean): ViewAssertion {
        val matcher = if (isVisible) {
            ViewMatchers.isDisplayed()
        } else {
            IsNot.not(ViewMatchers.isDisplayed())
        }
        return matches(
            Matchers.atPosition(
                position,
                layoutId,
                matcher
            )
        )
    }
}
