package com.jesuslcorominas.posts.app.di

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.jesuslcorominas.posts.app.data.analytics.AnalyticsTrackerImpl
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context = context

    @Singleton
    @Provides
    fun providesFirebaseAnalytics(context: Context): FirebaseAnalytics =
        FirebaseAnalytics.getInstance(context)

    @Singleton
    @Provides
    fun providesAnalyticsTracker(firebaseAnalytics: FirebaseAnalytics): AnalyticsTracker =
        AnalyticsTrackerImpl(firebaseAnalytics)
}