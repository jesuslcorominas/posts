package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Singleton
    @Provides
    fun providesPostRepository(
        localDatasource: LocalDatasource,
        remoteDatasource: RemoteDatasource
    ) = PostRepository(localDatasource, remoteDatasource)
}