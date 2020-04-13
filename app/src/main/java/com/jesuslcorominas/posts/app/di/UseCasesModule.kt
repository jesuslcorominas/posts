package com.jesuslcorominas.posts.app.di

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.usecases.GetPostDetailUseCase
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {

    @Singleton
    @Provides
    fun providesGetPostUseCase(postRepository: PostRepository): GetPostUseCase =
        GetPostUseCase(postRepository)

    @Singleton
    @Provides
    fun providesGetPostDetailUseCase(postRepository: PostRepository): GetPostDetailUseCase =
        GetPostDetailUseCase(postRepository)

}