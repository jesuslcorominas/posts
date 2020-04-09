package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostsRepository

class GetPostsUseCase(private val postsRepository: PostsRepository) {

    fun getPosts() {
        postsRepository.getPosts()
    }
}