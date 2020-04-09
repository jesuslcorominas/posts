package com.jesuslcorominas.posts.app.data.remote.datasource

import com.jesuslcorominas.posts.app.data.remote.model.toDomainPost
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.ServerException
import io.reactivex.Single
import java.io.IOException
import com.jesuslcorominas.posts.domain.Post as DomainPost

class PostRemoteDatasourceImpl(private val remoteService: RemoteService) : PostRemoteDatasource {

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
}