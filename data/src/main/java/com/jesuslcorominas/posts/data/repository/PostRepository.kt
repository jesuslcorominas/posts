package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.Post

class PostRepository(
    private val postLocalDatasource: PostLocalDatasource,
    private val postRemoteDatasource: PostRemoteDatasource
) {

    fun getPosts(): List<Post> {
        if (postLocalDatasource.isEmpty()) {
            postLocalDatasource.savePosts(postRemoteDatasource.getPosts())
        }

        return postLocalDatasource.getPosts()
    }
}
