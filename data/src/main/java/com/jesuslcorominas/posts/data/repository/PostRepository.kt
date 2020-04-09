package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.Post

class PostRepository(
    private val postLocalDatasource: PostLocalDatasource,
    private val postRemoteDatasource: PostRemoteDatasource
) {

    // TODO hacer reactivo
    fun getPosts(): List<Post> {
        if (postLocalDatasource.isEmpty()) {
            postRemoteDatasource.getPosts().subscribe { posts, error ->
                postLocalDatasource.savePosts(posts)
            }
        }

        return postLocalDatasource.getPosts()
    }
}
