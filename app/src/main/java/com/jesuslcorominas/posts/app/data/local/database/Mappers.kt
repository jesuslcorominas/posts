package com.jesuslcorominas.posts.app.data.local.database

import com.jesuslcorominas.posts.app.data.local.database.Author as DbAuthor
import com.jesuslcorominas.posts.app.data.local.database.Comment as DbComment
import com.jesuslcorominas.posts.app.data.local.database.Post as DbPost
import com.jesuslcorominas.posts.domain.Author as DomainAuthor
import com.jesuslcorominas.posts.domain.Comment as DomainComment
import com.jesuslcorominas.posts.domain.Post as DomainPost

// TODO ojo aqui a la hora de obtener los comentarios
fun DbPost.toDomainPost() = DomainPost(id, userId, title, body, author = null, comments = null)

// TODO cuidado con la conversion de author y comments
fun DomainPost.toDbPost() = DbPost(id, userId, title, body, null)

fun DomainAuthor.toDbAuthor() = DbAuthor(id, name, email)
fun DbAuthor.toDomainAuthor() = DomainAuthor(id, name, email)

fun DomainComment.toDbComment() = DbComment(id, postId, name, email, body)
fun DbComment.toDomainComment() = DomainComment(id, postId, name, email, body)

