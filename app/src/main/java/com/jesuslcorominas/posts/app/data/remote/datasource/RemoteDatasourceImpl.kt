package com.jesuslcorominas.posts.app.data.remote.datasource


import com.jesuslcorominas.posts.app.data.remote.service.*
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.ServerException
import io.reactivex.Single
import retrofit2.Response
import java.io.IOException
import com.jesuslcorominas.posts.domain.Post as DomainPost

class RemoteDatasourceImpl(private val remoteService: RemoteService) : RemoteDatasource {

    override fun getPosts(): Single<List<DomainPost>> {
        return Single.create { emitter ->
            try {
                val response = remoteService.api().getPosts().execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emitter.onSuccess(body.map { it.toDomainPost() })
                    } else {
                        emitter.onError(InvalidResponseException())
                    }
                } else {
                    emitter.onError(response.serverException())
                }
            } catch (e: IOException) {
                emitter.onError(ConnectionException())
            }
        }
    }

    override fun getPostDetail(post: DomainPost): Single<DomainPost> {
        return Single.create { emitter ->
            try {
                val authorResponse = remoteService.api().getAuthor(post.userId).execute()
                if (authorResponse.isSuccessful) {
                    val author = authorResponse.body()
                    if (author != null) {
                        val commentsResponse = remoteService.api().getComments(post.id).execute()
                        emitter.onSuccess(detailedPost(post, author, getComments(commentsResponse)))
                    } else {
                        emitter.onError(InvalidResponseException())
                    }
                } else {
                    emitter.onError(authorResponse.serverException())
                }
            } catch (e: IOException) {
                emitter.onError(ConnectionException())
            }
        }
    }

    private fun getComments(response: Response<List<Comment>>): List<Comment> =
        if (response.isSuccessful && response.body() != null) {
            response.body() as List<Comment>
        } else {
            ArrayList()
        }

    private fun detailedPost(post: DomainPost, author: Author, comments: List<Comment>) =
        post.copy(
            author = author.toDomainAuthor(),
            comments = comments.map { it.toDomainComment() })

    private fun <T : Any> Response<T>.serverException() = ServerException(code(), message())
}