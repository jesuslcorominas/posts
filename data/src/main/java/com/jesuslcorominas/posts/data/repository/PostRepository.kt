package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.DatabaseEmptyException
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Single

class PostRepository(
    private val localDatasource: LocalDatasource,
    private val remoteDatasource: RemoteDatasource
) {

    fun getPosts(): Single<List<Post>> =
        localDatasource.getPosts()
            .switchIfEmpty(
                remoteDatasource.getPosts()
                    .flatMap { items ->
                        localDatasource.savePosts(items)
                            .andThen(localDatasource.getPosts())
                            .switchIfEmpty(Single.error(DatabaseEmptyException()))
                    }
            )


    fun getPostDetail(postId: Int): Single<Post> {
        return localDatasource.getPostDetail(postId)
    }
}
