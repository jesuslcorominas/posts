package com.jesuslcorominas.posts.app.ui.detail

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.usecases.GetPostDetailUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailModule(private val postId: Int) {

    @Provides
    fun providesDetailViewModel(
        getPostDetailUseCase: GetPostDetailUseCase
    ): DetailViewModel {
        return DetailViewModel(postId, getPostDetailUseCase)
    }

    @Provides
    fun providesGetPostDetailUseCase(postRepository: PostRepository) =
        GetPostDetailUseCase(postRepository)

}

@Subcomponent(modules = [(DetailModule::class)])
interface DetailComponent {
    val detaiViewModel: DetailViewModel
}