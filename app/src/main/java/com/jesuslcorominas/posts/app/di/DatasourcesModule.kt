package com.jesuslcorominas.posts.app.di

import android.content.Context
import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.datasource.LocalDatasourceImpl
import com.jesuslcorominas.posts.app.data.remote.datasource.RemoteDatasourceImpl
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DatasourcesModule {

    companion object {
        const val NAME_BASE_URL = "base_url"

        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    }

    @Singleton
    @Provides
    fun providesDatabase(context: Context) = PostDatabase.build(context)

    @Singleton
    @Provides
    @Named(NAME_BASE_URL)
    fun providesBaseUrl() = BASE_URL

    @Singleton
    @Provides
    fun providesRemoteService(@Named(NAME_BASE_URL) baseUrl: String) = RemoteService(baseUrl)

    @Singleton
    @Provides
    fun providesPostLocalDatasource(postDatabase: PostDatabase): LocalDatasource =
        LocalDatasourceImpl(postDatabase)

    @Singleton
    @Provides
    fun providesPostRemoteDatasource(remoteService: RemoteService): RemoteDatasource =
        RemoteDatasourceImpl(remoteService)
}