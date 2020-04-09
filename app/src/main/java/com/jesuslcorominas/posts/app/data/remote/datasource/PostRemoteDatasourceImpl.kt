package com.jesuslcorominas.posts.app.data.remote.datasource

import com.jesuslcorominas.posts.app.data.remote.model.toDomainPost
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.ServerException
import java.io.IOException
import com.jesuslcorominas.posts.domain.Post as DomainPost

class PostRemoteDatasourceImpl(private val remoteService: RemoteService) : PostRemoteDatasource {

    override fun getPosts(): List<DomainPost> {
        try {
            val response = remoteService.remoteApi().getPosts().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return body.map { it.toDomainPost() }
//                } else {
//                    throw InvalidResponseException()
                }
            }

            throw ServerException(response.code(), response.message())
        } catch (e: IOException) {
            throw ConnectionException()
        }
    }
}