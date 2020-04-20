package com.jesuslcorominas.posts.app.data.local.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Post(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)

@Entity
data class Author(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)

@Entity
data class Comment(
    @PrimaryKey val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)

data class PostDetail(
    @Embedded val post: Post,
    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val author: Author?,
    @Relation(
        parentColumn = "id",
        entityColumn = "postId"
    )
    val comments: List<Comment>?
)
