package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository

class GetPostUseCase(private val postRepository: PostRepository) {

    fun getPosts() = postRepository.getPosts()
}