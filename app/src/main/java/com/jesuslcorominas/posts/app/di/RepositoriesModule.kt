package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Singleton
    @Provides
    fun providesPostRepository(
        localDatasource: PostLocalDatasource,
        remoteDatasource: PostRemoteDatasource
    ) = PostRepository(localDatasource, remoteDatasource)
}