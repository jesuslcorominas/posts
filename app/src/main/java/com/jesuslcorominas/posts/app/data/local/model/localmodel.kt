package com.jesuslcorominas.posts.app.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Post(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)


