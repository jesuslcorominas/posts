package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.database.toDbPost
import com.jesuslcorominas.posts.app.data.local.database.toDomainPost
import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Single

class PostLocalDatasourceImpl(private val postDatabase: PostDatabase) : PostLocalDatasource {

    override fun isEmpty(): Single<Boolean> {
        val isEmpty = postDatabase.postDao().postCount() == 0

        return Single.create { emitter ->
            emitter.onSuccess(isEmpty)
        }

    }

    override fun getPosts(): Single<List<Post>> {
        val storedPosts: List<Post> = postDatabase.postDao().getAll().map { it.toDomainPost() }

        return Single.create { emitter ->
            emitter.onSuccess(storedPosts)
        }
    }

    override fun savePosts(posts: List<Post>): Completable {
        postDatabase.postDao().insertPosts(posts.map { it.toDbPost() })

        return Completable.complete()
    }


}