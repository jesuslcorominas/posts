package com.jesuslcorominas.posts.app.di

import android.content.Context
import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun providesContext(): Context = context
}