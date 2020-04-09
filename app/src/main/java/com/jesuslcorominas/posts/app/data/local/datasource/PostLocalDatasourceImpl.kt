package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.model.toDbPost
import com.jesuslcorominas.posts.app.data.local.model.toDomainPost
import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.domain.Post

class PostLocalDatasourceImpl(private val postDatabase: PostDatabase) : PostLocalDatasource {

    override fun isEmpty() = postDatabase.postDao().postCount() == 0

    override fun getPosts() = postDatabase.postDao().getAll().map { it.toDomainPost() }

    override fun savePosts(posts: List<Post>) =
        postDatabase.postDao().insertPosts(posts.map { it.toDbPost() })
}