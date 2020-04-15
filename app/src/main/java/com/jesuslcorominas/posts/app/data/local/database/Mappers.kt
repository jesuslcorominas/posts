package com.jesuslcorominas.posts.app.data.local.database

import com.jesuslcorominas.posts.app.data.local.database.Author as DbAuthor
import com.jesuslcorominas.posts.app.data.local.database.Comment as DbComment
import com.jesuslcorominas.posts.app.data.local.database.Post as DbPost
import com.jesuslcorominas.posts.domain.Author as DomainAuthor
import com.jesuslcorominas.posts.domain.Comment as DomainComment
import com.jesuslcorominas.posts.domain.Post as DomainPost

fun DbPost.toDomainPost() = DomainPost(id, userId, title, body, author = null, comments = ArrayList())
fun DomainPost.toDbPost() = DbPost(id, userId, title, body)

fun DomainAuthor.toDbAuthor() = DbAuthor(id, name, email)
fun DbAuthor.toDomainAuthor() = DomainAuthor(id, name, email)

fun DomainComment.toDbComment() = DbComment(id, postId, name, email, body)
fun DbComment.toDomainComment() = DomainComment(id, postId, name, email, body)

fun PostDetail.toDomainPost() = DomainPost(
    post.id,
    post.userId,
    post.title,
    post.body,
    author?.toDomainAuthor(),
    comments?.map { it.toDomainComment() } ?: ArrayList())

