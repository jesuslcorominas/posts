package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class PostRepository(
    private val postLocalDatasource: PostLocalDatasource,
    private val postRemoteDatasource: PostRemoteDatasource
) {

    // TODO hacer reactivo
    fun getPosts(): Single<List<Post>> {
//        postLocalDatasource.isEmpty().if
//
//        if (postLocalDatasource.isEmpty()) {
//            postRemoteDatasource.getPosts().subscribe { posts, error ->
//                postLocalDatasource.savePosts(posts)
//            }
//        }

        return postLocalDatasource.getPosts()
    }
}
