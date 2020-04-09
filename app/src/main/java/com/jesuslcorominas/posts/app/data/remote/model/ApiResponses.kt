package com.jesuslcorominas.posts.app.data.remote.model

data class Post(val id: Int, val userId: Int, val title: String, val body: String)

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