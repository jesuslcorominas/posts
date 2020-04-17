package com.jesuslcorominas.posts.domain.analytics

interface AnalyticsTracker {

    fun track(event: AnalyticsEvent)
}

open class AnalyticsEvent(val name: String, val params: Map<String, Any?> = HashMap())
