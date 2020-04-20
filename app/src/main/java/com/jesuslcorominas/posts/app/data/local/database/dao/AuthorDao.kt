package com.jesuslcorominas.posts.app.data.local.database.dao

import androidx.room.Dao
import com.jesuslcorominas.posts.app.data.local.database.Author

@Dao
interface AuthorDao :
    AbstractDao<Author> {

}