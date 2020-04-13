package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDatasource {

    fun isEmpty(): Single<Boolean>

    fun getPosts(): Single<List<Post>>

    fun savePosts(posts: List<Post>): Completable

    fun getPostDetail(postId: Int): Single<Post>
}
