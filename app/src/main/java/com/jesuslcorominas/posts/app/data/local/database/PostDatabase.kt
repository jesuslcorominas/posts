package com.jesuslcorominas.posts.app.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Post::class], version = 1)
abstract class PostDatabase : RoomDatabase() {

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            PostDatabase::class.java,
            "post-db"
        ).build()
    }

    abstract fun postDao(): PostDao
}
