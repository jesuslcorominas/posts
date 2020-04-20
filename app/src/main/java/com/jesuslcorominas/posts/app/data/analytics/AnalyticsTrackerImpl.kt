package com.jesuslcorominas.posts.app.data.analytics

import com.google.firebase.analytics.FirebaseAnalytics
import com.jesuslcorominas.posts.data.source.AnalyticsEvent
import com.jesuslcorominas.posts.data.source.AnalyticsTracker

class AnalyticsTrackerImpl(private val firebaseAnalytics: FirebaseAnalytics) :
    AnalyticsTracker {

    override fun track(event: AnalyticsEvent) {
        firebaseAnalytics.logEvent(event.name, event.params.toBundle())
    }
}