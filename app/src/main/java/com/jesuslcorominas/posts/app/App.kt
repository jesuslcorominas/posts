package com.jesuslcorominas.posts.app

import android.app.Application
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.di.ApplicationModule
import com.jesuslcorominas.posts.app.di.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }

}