package com.jesuslcorominas.posts.app.data.local.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesuslcorominas.posts.app.data.local.model.Post

interface PostDao {

    @Query("SELECT count(id) FROM Post")
    fun postCount(): Int

    @Query("SELECT * from Post")
    fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPosts(posts: List<Post>)
}
