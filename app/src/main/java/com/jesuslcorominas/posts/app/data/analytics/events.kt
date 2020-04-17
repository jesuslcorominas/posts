package com.jesuslcorominas.posts.app.data.analytics

import com.jesuslcorominas.posts.data.source.AnalyticsEvent

class ClickPostEvent(private val postId: Int) :
    AnalyticsEvent("ClickPost", mapOf("postId" to postId))

class GetPostsEvent() : AnalyticsEvent("GetPosts")