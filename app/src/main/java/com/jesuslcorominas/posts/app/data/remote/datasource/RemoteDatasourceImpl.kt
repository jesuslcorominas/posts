package com.jesuslcorominas.posts.app.data.remote.datasource


import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.app.data.remote.service.toDomainAuthor
import com.jesuslcorominas.posts.app.data.remote.service.toDomainComment
import com.jesuslcorominas.posts.app.data.remote.service.toDomainPost
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.ServerException
import io.reactivex.Single
import java.io.IOException
import com.jesuslcorominas.posts.domain.Post as DomainPost

class RemoteDatasourceImpl(private val remoteService: RemoteService) : RemoteDatasource {

    override fun getPosts(): Single<List<DomainPost>> {
        return Single.create { emitter ->
            try {
                val response = remoteService.remoteApi().getPosts().execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emitter.onSuccess(body.map { it.toDomainPost() })
                    } else {
                        emitter.onError(InvalidResponseException())
                    }
                } else {
                    emitter.onError(ServerException(response.code(), response.message()))
                }
            } catch (e: IOException) {
                emitter.onError(ConnectionException())
            }
        }
    }

    override fun getPostDetail(post: DomainPost): Single<DomainPost> {
        return Single.create { emitter ->
            try {
                val getAuthorResponse = remoteService.remoteApi().getAuthor(post.userId).execute()
                if (getAuthorResponse.isSuccessful) {
                    val getAuthorBody = getAuthorResponse.body()
                    if (getAuthorBody != null) {
                        val author = getAuthorBody.toDomainAuthor()

                        val getCommentsResponse =
                            remoteService.remoteApi().getComments(post.id).execute()
                        if (getCommentsResponse.isSuccessful) {
                            val getCommentsBody = getCommentsResponse.body()
                            if (getCommentsBody != null) {
                                val comments = getCommentsBody.map { it.toDomainComment() }

                                val postDetail = post.copy(author = author, comments = comments)
                                emitter.onSuccess(postDetail)
                            } else {
                                emitter.onError(InvalidResponseException())
                            }
                        } else {
                            emitter.onError(
                                ServerException(
                                    getCommentsResponse.code(),
                                    getCommentsResponse.message()
                                )
                            )
                            return@create
                        }
                    } else {
                        emitter.onError(InvalidResponseException())
                    }
                } else {
                    emitter.onError(
                        ServerException(
                            getAuthorResponse.code(),
                            getAuthorResponse.message()
                        )
                    )
                }
            } catch (e: IOException) {
                emitter.onError(ConnectionException())
            }
        }
    }
}