package com.jesuslcorominas.posts.app.ui

import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TrampolineSchedulerProvider : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun computation(): Scheduler = Schedulers.trampoline()
}