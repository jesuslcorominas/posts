package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post

interface PostLocalDatasource {

    fun isEmpty(): Boolean

    fun getPosts(): List<Post>

    fun savePosts(posts: List<Post>)
}
