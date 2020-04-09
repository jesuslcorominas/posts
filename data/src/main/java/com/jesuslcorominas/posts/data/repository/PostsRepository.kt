package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.Post

class PostsRepository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {

    fun getPosts(): List<Post> {
        if (localDatasource.isEmpty()) {
            remoteDatasource.getPosts()
        }

        return localDatasource.getPosts()
    }
}
