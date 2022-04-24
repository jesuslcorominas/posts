package com.jesuslcorominas.posts.app.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.util.SuccessDispatcher
import com.jesuslcorominas.posts.app.util.fromJson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NavHostActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(NavHostActivity::class.java, false, false)

    var webServer: MockWebServer? = null

    @Before
    fun setUp() {
        val thread = Thread(Runnable {
            webServer = MockWebServer()
            webServer?.start(8080)
            webServer?.dispatcher = SuccessDispatcher()
        })

        thread.start()
        thread.join()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer?.shutdown()
    }

    @Test
    fun testing() {
        activityTestRule.launchActivity(null)

        performClickOnRecyclerViewFirstItem()
        checkHasNavigatedToDetail()
    }

    private fun performClickOnRecyclerViewFirstItem() =
        Espresso.onView(ViewMatchers.withId(R.id.recyclerViewPosts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                ViewActions.click()
            )
        )

    private fun checkHasNavigatedToDetail() =
        Espresso.onView(ViewMatchers.withId(R.id.textViewDetailTitle))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText("titulo de prueba"))))

    private fun enqueue(jsonFile: String) {
        webServer?.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                jsonFile
            )
        )
    }

}