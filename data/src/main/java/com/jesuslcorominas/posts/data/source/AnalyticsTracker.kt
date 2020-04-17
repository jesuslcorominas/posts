package com.jesuslcorominas.posts.data.source

interface AnalyticsTracker {

    fun track(event: AnalyticsEvent)
}

open class AnalyticsEvent(val name: String, val params: Map<String, Any?> = HashMap())
