package com.jesuslcorominas.posts.app.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface AbstractDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(items: List<T>)
}