package com.jesuslcorominas.posts.app.data.analytics

import com.jesuslcorominas.posts.data.source.AnalyticsEvent

class ClickPostEvent(private val postId: Int) :
    AnalyticsEvent("ClickPost", mapOf("postId" to postId))

class GetPostsEvent() : AnalyticsEvent("getPosts")

class GetPostsErrorEvent(private val error: String?) :
    AnalyticsEvent("getPostsError", mapOf("error" to error))

class GetPostDetailEvent(private val postId: Int) :
    AnalyticsEvent("getPostDetail", mapOf("postId" to postId))

class GetPostDetailErrorEvent(private val postId: Int, private val error: String?) :
    AnalyticsEvent("getPostDetail", mapOf("postId" to postId, "error" to error))