package com.jesuslcorominas.posts.app.data.remote.service

import com.jesuslcorominas.posts.app.data.remote.service.Author as RemoteAuthor
import com.jesuslcorominas.posts.app.data.remote.service.Comment as RemoteComment
import com.jesuslcorominas.posts.app.data.remote.service.Post as RemotePost
import com.jesuslcorominas.posts.domain.Author as DomainAuthor
import com.jesuslcorominas.posts.domain.Comment as DomainComment
import com.jesuslcorominas.posts.domain.Post as DomainPost

fun RemotePost.toDomainPost() = DomainPost(id, userId, title, body, null, ArrayList())
fun DomainPost.toRemotePost() = RemotePost(id, userId, title, body)

fun RemoteAuthor.toDomainAuthor() = DomainAuthor(id, name, email)
fun DomainAuthor.toRemoteAuthor() = RemoteAuthor(id, name, email)

fun RemoteComment.toDomainComment() = DomainComment(id, postId, name, email, body)
fun DomainComment.toRemoteComment() = RemoteComment(id, postId, name, email, body)

