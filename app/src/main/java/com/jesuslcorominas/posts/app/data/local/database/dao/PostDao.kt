package com.jesuslcorominas.posts.app.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.jesuslcorominas.posts.app.data.local.database.Post
import com.jesuslcorominas.posts.app.data.local.database.PostDetail

@Dao
interface PostDao :
    AbstractDao<Post> {

    @Query("SELECT * from Post")
    fun getAll(): List<Post>

    @Query("SELECT * from Post where id = :postId")
    fun findPostById(postId: Int): Post

    @Transaction
    @Query("SELECT * FROM Post where id = :postId")
    fun getPostWithComments(postId: Int): PostDetail
}
