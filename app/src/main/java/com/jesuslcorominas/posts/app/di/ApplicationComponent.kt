package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.app.ui.detail.DetailFragment
import com.jesuslcorominas.posts.app.ui.main.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, FactoriesModule::class,
        UseCasesModule::class, RepositoriesModule::class, DatasourcesModule::class]
)
interface ApplicationComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(detailFragment: DetailFragment)
}