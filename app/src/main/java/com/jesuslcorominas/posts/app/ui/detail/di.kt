package com.jesuslcorominas.posts.app.ui.detail

import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.usecases.GetPostDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailModule(private val postId: Int) {

    @Provides
    fun providesDetailViewModel(
        getPostDetailUseCase: GetPostDetailUseCase,
        analyticsTracker: AnalyticsTracker,
        schedulerProvider: SchedulerProvider
    ): DetailViewModel {
        return DetailViewModel(postId, getPostDetailUseCase, analyticsTracker, schedulerProvider)
    }

    @Provides
    fun providesGetPostDetailUseCase(postRepository: PostRepository) =
        GetPostDetailUseCase(postRepository)

}

@Subcomponent(modules = [(DetailModule::class)])
interface DetailComponent {
    val detaiViewModel: DetailViewModel
}