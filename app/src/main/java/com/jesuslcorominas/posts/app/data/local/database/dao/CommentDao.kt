package com.jesuslcorominas.posts.app.data.local.database.dao

import androidx.room.Dao
import com.jesuslcorominas.posts.app.data.local.database.Comment

@Dao
interface CommentDao :
    AbstractDao<Comment> {


}