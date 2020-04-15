package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class GetPostUseCase(private val postRepository: PostRepository) {

    fun getPosts() = postRepository.getPosts()

//    fun getPosts(): Single<List<Post>> = Single.create {
//        it.onError(ConnectionException())
//    }
}