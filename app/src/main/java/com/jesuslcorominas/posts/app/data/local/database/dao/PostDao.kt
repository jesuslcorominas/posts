package com.jesuslcorominas.posts.app.data.local.database.dao

import androidx.room.*
import com.jesuslcorominas.posts.app.data.local.database.Post
import com.jesuslcorominas.posts.app.data.local.database.PostDetail

@Dao
interface PostDao :
    AbstractDao<Post> {

    @Query("SELECT * from Post")
    fun getAll(): List<Post>

    @Update
    fun updatePost(post: Post)

    @Transaction
    @Query("SELECT * FROM Post where id = :postId")
    fun getPostWithComments(postId: Int): PostDetail
}
