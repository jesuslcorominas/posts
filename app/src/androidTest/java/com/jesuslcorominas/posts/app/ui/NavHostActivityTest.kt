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
import kotlin.concurrent.thread

class NavHostActivityTest {

    @get:Rule
    val activityTestRule = ActivityTestRule(NavHostActivity::class.java, false, false)

    lateinit var webServer: MockWebServer

    @Before
    fun setUp() {
        webServer = MockWebServer()
        webServer.start(8080)
        webServer.dispatcher = SuccessDispatcher()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        webServer.shutdown()
    }

    @Test
    fun testing() {
        activityTestRule.launchActivity(null)

//        performClickOnRecyclerViewFirstItem()
//        waitMillis(1000)

//        checkHasNavigatedToDetail()

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
        webServer.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                jsonFile
            )
        )
    }

    private fun askMockServerUrlOnAnotherThread(): String {
        /*
        This needs to be done immediately, but the App will crash with
        "NetworkOnMainThreadException" if this is not extracted from the main thread. So this is
        a "hack" to prevent it. We don't care about blocking in a test, and it's fast.
        */
        var url = ""
        val t = thread {
            url = webServer.url("/").toString()
        }
        t.join()
        return url
    }

}