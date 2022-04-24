package com.jesuslcorominas.posts.app.util

import android.content.Context
import android.net.Uri
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class SuccessDispatcher(
    private val context: Context = InstrumentationRegistry.getInstrumentation().context
) : Dispatcher() {
    private val responseFilesByPath: Map<String, String> = mapOf(
        "posts" to "posts.json",
        "users/{id}" to "author.json",
        "posts/{id}/comments" to "comments.json"
    )

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

        val pathWithoutQueryParams = Uri.parse(request.path).path ?: return errorResponse
        val responseFile = responseFilesByPath[pathWithoutQueryParams]

        return if (responseFile != null) {
            val responseBody = AssetReaderUtil.asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}