package com.jesuslcorominas.posts.app

import com.jesuslcorominas.posts.app.di.ApplicationModule
import com.jesuslcorominas.posts.app.di.DaggerApplicationComponent
import com.jesuslcorominas.posts.app.di.DataModule

class TestApp : App() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .dataModule(DataModule("http://localhost:8080/"))
            .build();
    }
}