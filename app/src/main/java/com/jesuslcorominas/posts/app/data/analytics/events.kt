package com.jesuslcorominas.posts.app.data.analytics

import com.jesuslcorominas.posts.data.source.AnalyticsEvent

class ClickPostEvent(private val postId: Int) :
    AnalyticsEvent("click_post", mapOf("post_id" to postId))

class GetPostsEvent() : AnalyticsEvent("get_posts")

class GetPostsErrorEvent(private val error: String?) :
    AnalyticsEvent("get_posts_error", mapOf("error" to error))

class GetPostDetailEvent(private val postId: Int) :
    AnalyticsEvent("get_post_detail", mapOf("post_id" to postId))

class GetPostDetailErrorEvent(private val postId: Int, private val error: String?) :
    AnalyticsEvent("get_post_detail_error", mapOf("post_id" to postId, "error" to error))