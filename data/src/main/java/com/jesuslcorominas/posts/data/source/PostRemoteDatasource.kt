package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

interface PostRemoteDatasource {

    fun getPosts(): Single<List<Post>>
}
