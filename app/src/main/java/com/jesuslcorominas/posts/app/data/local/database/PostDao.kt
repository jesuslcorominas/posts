package com.jesuslcorominas.posts.app.data.local.database

import androidx.room.*

@Dao
interface PostDao {

    @Query("SELECT count(id) FROM Post")
    fun postCount(): Int

    @Query("SELECT * from Post")
    fun getAll(): List<Post>

    @Update
    fun updatePost(post: Post)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPosts(posts: List<Post>)

    @Transaction
    @Query("SELECT * FROM Post where id = :postId")
    fun getPostWithComments(postId: Int): PostWithComments
}
