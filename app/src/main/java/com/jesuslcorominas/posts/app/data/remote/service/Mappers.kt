package com.jesuslcorominas.posts.app.data.remote.service

import com.jesuslcorominas.posts.app.data.remote.service.Post as RemotePost
import com.jesuslcorominas.posts.domain.Post as DomainPost

fun RemotePost.toDomainPost() = DomainPost(id, userId, title, body)
fun DomainPost.toRemotePost() = RemotePost(id, userId, title, body)