package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.app.ui.detail.DetailComponent
import com.jesuslcorominas.posts.app.ui.detail.DetailModule
import com.jesuslcorominas.posts.app.ui.main.MainComponent
import com.jesuslcorominas.posts.app.ui.main.MainModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, DataModule::class]
)
interface ApplicationComponent {

    fun plus(module: MainModule): MainComponent
    fun plus(module: DetailModule): DetailComponent

}