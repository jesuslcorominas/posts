package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class GetPostUseCase(private val postRepository: PostRepository) {

//    fun getPosts() = postRepository.getPosts()

    fun getPosts(): Single<List<Post>> = Single.create {
        val posts = ArrayList<Post>()
        for (i in 1..10) {
            posts.add(mockedPost.copy(i))
        }

        it.onSuccess(posts)
//        it.onError(ConnectionException())
    }
}

val mockedPost = Post(
    0,
    1,
    "Apple y Google se alían frente al COVID-19 e integrarán un sistema de seguimiento de contagios basado en Bluetooth en iOS y Android",
    "Apple y Google han decidido intervenir. Las dos grandes compañías acaban de anunciar la creación de un sistema de seguimiento que estará" +
            " integrado en iOS y Android. ¿La idea? Poner a disposición de las autoridades sanitarias herramientas que permitan el desarrollo de " +
            "aplicaciones para intentar controlar la propagación del virus COVID-19."
)