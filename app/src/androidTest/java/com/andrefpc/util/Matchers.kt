package com.andrefpc.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object Matchers {
    /**
     * Matcher to test a condition in one of the items params of the RecyclerView position
     * @param position Position into the RecyclerView list
     * @param resourceId The resource id of the field to be compared
     * @param itemMatcher The matcher to be used
     *
     */
    fun atPosition(position: Int, resourceId: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item at position $position: ")
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(view: RecyclerView): Boolean {
                val viewHolder =
                    view.findViewHolderForAdapterPosition(position) // has no item on such position
                        ?: return false
                return itemMatcher.matches(viewHolder.itemView.findViewById(resourceId))
            }


        }
    }
}
