package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

interface RemoteDatasource {

    fun getPosts(): Single<List<Post>>

    fun getAuthorDetail(id: Int)

    fun getComments(postId: Int)
}
