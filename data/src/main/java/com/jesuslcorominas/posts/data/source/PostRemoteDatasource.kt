package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post

interface PostRemoteDatasource {

    fun getPosts(): List<Post>
}
