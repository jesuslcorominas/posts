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
//        if (postLocalDatasource.isEmpty()) {
//                postLocalDatasource.savePosts(posts)
//        }

//        return postLocalDatasource.getPosts()
        return postRemoteDatasource.getPosts()
    }
}
