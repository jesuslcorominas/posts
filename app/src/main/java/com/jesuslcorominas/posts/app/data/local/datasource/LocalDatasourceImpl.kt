package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.*
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.domain.Author
import com.jesuslcorominas.posts.domain.Comment
import com.jesuslcorominas.posts.domain.Post
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import timber.log.Timber

class LocalDatasourceImpl(private val postDatabase: PostDatabase) : LocalDatasource {

    override fun getPosts(): Maybe<List<Post>> = Maybe.create { emitter ->
        val storedPosts: List<Post> = postDatabase.postDao().getAll().map { it.toDomainPost() }
        if (storedPosts.isEmpty()) {
            emitter.onComplete()
        } else {
            emitter.onSuccess(storedPosts)
        }
    }

    override fun savePosts(posts: List<Post>): Completable {
        try {
            postDatabase.runInTransaction {
                postDatabase.postDao().insert(posts.map { it.toDbPost() })

                val authors: Set<Author> = HashSet()
                val comments: Set<Comment> = HashSet()
                posts.forEach { post ->
                    post.author?.let { author ->
                        authors.plus(author)
                    }

                    post.comments?.let { comments ->
                        comments.plus(comments.iterator())
                    }
                }
                postDatabase.authorDao().insert(authors.toList().map { it.toDbAuthor() })
                postDatabase.commentDao().insert(comments.toList().map { it.toDbComment() })
            }

            return Completable.complete()
        } catch (e: Exception) {
            Timber.e(e, "Error al insertar post en transaccion")
            return Completable.error(e)
        }
    }

    override fun getPostDetail(postId: Int): Single<Post> = Single.create { emitter ->
        emitter.onSuccess(postDatabase.postDao().getPostWithComments(postId).toDomainPost())
    }
}