package com.jesuslcorominas.posts.app.data.local.database

import com.jesuslcorominas.posts.app.data.local.database.Post as DbPost
import com.jesuslcorominas.posts.domain.Post as DomainPost

fun DbPost.toDomainPost() = DomainPost(id, userId, title, body)

fun DomainPost.toDbPost() = DbPost(id, userId, title, body)