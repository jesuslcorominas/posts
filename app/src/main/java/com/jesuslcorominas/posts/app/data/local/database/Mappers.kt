package com.jesuslcorominas.posts.app.data.local.database

import com.jesuslcorominas.posts.app.data.local.database.Post as DbPost
import com.jesuslcorominas.posts.domain.Post as DomainPost

// TODO ojo aqui a la hora de obtener los comentarios
fun DbPost.toDomainPost() = DomainPost(id, userId, title, body, author = null, comments = null)

// TODO cuidado con la conversion de author y comments
fun DomainPost.toDbPost() = DbPost(id, userId, title, body, null)