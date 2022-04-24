package com.jesuslcorominas.posts.app.util

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.jesuslcorominas.posts.app.TestApp
import com.squareup.rx2.idler.Rx2Idler


class MockRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    @Throws(
        InstantiationException::class,
        IllegalAccessException::class,
        ClassNotFoundException::class
    )
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application? {
        return super.newApplication(cl, TestApp::class.java.getName(), context)
    }

    override fun onStart() {
        Rx2Idler.create("RxJava 2.x Computation Scheduler")

        super.onStart()
    }
}