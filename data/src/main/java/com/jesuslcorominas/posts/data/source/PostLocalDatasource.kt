package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Single

interface PostLocalDatasource {

    fun isEmpty(): Single<Boolean>

    fun getPosts(): Single<List<Post>>

    fun savePosts(posts: List<Post>): Completable
}
