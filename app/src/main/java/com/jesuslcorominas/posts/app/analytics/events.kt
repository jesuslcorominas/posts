package com.jesuslcorominas.posts.app.analytics

import com.jesuslcorominas.posts.domain.analytics.AnalyticsEvent

class ClickPostEvent(private val postId: Int) :
    AnalyticsEvent("ClickPost", mapOf("postId" to postId))

class GetPostsEvent() : AnalyticsEvent("GetPosts")