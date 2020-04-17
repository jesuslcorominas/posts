package com.jesuslcorominas.posts.app.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.jesuslcorominas.posts.domain.analytics.AnalyticsEvent
import com.jesuslcorominas.posts.domain.analytics.AnalyticsTracker

class AnalyticsTrackerImpl(private val firebaseAnalytics: FirebaseAnalytics) : AnalyticsTracker {

    override fun track(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name, event.params.toBundle())
    }
}