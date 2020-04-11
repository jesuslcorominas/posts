package com.jesuslcorominas.posts.app.data.remote.service

data class Post(val id: Int, val userId: Int, val title: String, val body: String)

// TODO revisar al final si se usan o no
data class Author(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String
)

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)