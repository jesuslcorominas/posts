package com.jesuslcorominas.posts.app.util

import android.os.AsyncTask
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import rx.Scheduler
import rx.plugins.RxJavaHooks
import rx.schedulers.Schedulers


class AsyncTaskSchedulerRule : TestRule {
    val asyncTaskScheduler: Scheduler = Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR)

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaHooks.setOnIOScheduler { scheduler: Scheduler? -> asyncTaskScheduler }
                RxJavaHooks.setOnComputationScheduler { scheduler: Scheduler? -> asyncTaskScheduler }
                RxJavaHooks.setOnNewThreadScheduler { scheduler: Scheduler? -> asyncTaskScheduler }
                try {
                    base.evaluate()
                } finally {
                    RxJavaHooks.reset()
                }
            }
        }
    }
}