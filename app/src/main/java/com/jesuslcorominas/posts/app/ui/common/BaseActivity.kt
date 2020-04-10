package com.jesuslcorominas.posts.app.ui.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jesuslcorominas.posts.app.App
import com.jesuslcorominas.posts.app.di.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreate((application as App).appComponent, savedInstanceState)
    }

    /**
     * Calling when activity is starting. Include AppComponent for dependency injection
     * @param appComponent Dependency container
     * @param savedInstanceState Contains the previous data
     */
    abstract fun onCreate(appComponent: ApplicationComponent, savedInstanceState: Bundle?)
}