package com.armandorochin.themoviedb

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.armandorochin.themoviedb.ui.fragments.movies.MovieViewHolder
import com.armandorochin.themoviedb.ui.MainActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Rule @JvmField
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    private fun getActivity() = activityRule.activity
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.armandorochin.themoviedb", appContext.packageName)
    }

    @Test
    fun recyclerScroll() {
        val rv = this.activityRule.activity.findViewById<RecyclerView>(R.id.rvMovies)
        val count = rv.adapter?.itemCount ?: 19

        Espresso.onView(withId(R.id.rvMovies))
            .perform(RecyclerViewActions.scrollToPosition<MovieViewHolder>(count))
    }
}