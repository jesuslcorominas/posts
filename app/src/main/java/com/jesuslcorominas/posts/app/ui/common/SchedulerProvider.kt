package com.jesuslcorominas.posts.app.ui.common

import io.reactivex.Scheduler

interface SchedulerProvider {

    fun io(): Scheduler
    fun ui(): Scheduler
    fun computation(): Scheduler
}