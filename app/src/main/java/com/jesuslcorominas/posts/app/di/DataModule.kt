package com.jesuslcorominas.posts.app.di

import android.content.Context
import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.datasource.LocalDatasourceImpl
import com.jesuslcorominas.posts.app.data.remote.datasource.RemoteDatasourceImpl
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule (private val baseUrl:String){

    @Singleton
    @Provides
    fun providesDatabase(context: Context) = PostDatabase.build(context)

    @Singleton
    @Provides
    fun providesRemoteService() = RemoteService(baseUrl)

    @Singleton
    @Provides
    fun providesPostLocalDatasource(postDatabase: PostDatabase): LocalDatasource =
        LocalDatasourceImpl(postDatabase)

    @Singleton
    @Provides
    fun providesPostRemoteDatasource(remoteService: RemoteService): RemoteDatasource =
        RemoteDatasourceImpl(remoteService)

    @Singleton
    @Provides
    fun providesPostRepository(
        localDatasource: LocalDatasource,
        remoteDatasource: RemoteDatasource
    ) = PostRepository(localDatasource, remoteDatasource)
}