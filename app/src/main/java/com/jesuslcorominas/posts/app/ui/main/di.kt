package com.jesuslcorominas.posts.app.ui.main

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainModule {

    @Provides
    fun providesMainViewModel(getPostUseCase: GetPostUseCase, analyticsTracker: AnalyticsTracker) = MainViewModel(getPostUseCase, analyticsTracker)

    @Provides
    fun providesGetPostUseCase(postRepository: PostRepository) = GetPostUseCase(postRepository)
}

@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {
    val mainViewModel: MainViewModel
}