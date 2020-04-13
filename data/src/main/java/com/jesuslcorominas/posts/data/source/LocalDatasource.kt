package com.jesuslcorominas.posts.data.source

import com.jesuslcorominas.posts.domain.Author
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface LocalDatasource {

    fun getPosts(): Maybe<List<Post>>

    fun savePosts(posts: List<Post>): Completable

    fun getPostDetail(postId: Int): Single<Post>
}
