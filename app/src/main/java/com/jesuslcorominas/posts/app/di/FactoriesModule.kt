package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import com.jesuslcorominas.posts.usecases.GetPostUseCase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FactoriesModule {

    @Singleton
    @Provides
    fun providesViewModelFactory(getPostUseCase: GetPostUseCase) = ViewModelFactory(getPostUseCase)

}