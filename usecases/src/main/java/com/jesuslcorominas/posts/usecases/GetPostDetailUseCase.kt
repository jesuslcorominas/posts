package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class GetPostDetailUseCase(private val postRepository: PostRepository) {

    fun getPostDetailUseCase(postId: Int): Single<Post> = postRepository.getPostDetail(postId)
}