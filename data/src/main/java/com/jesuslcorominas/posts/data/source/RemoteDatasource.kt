package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

interface RemoteDatasource {

    fun getPosts(): Single<List<Post>>

    fun getPostDetail(postId: Int): Single<Post>
}
