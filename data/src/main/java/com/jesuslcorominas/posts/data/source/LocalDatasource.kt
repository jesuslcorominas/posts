package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post

interface LocalDatasource {

    fun isEmpty(): Boolean

    fun getPosts(): List<Post>
}
