package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.app.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, FactoriesModule::class,
        UseCasesModule::class, RepositoriesModule::class, DatasourcesModule::class]
)
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
}