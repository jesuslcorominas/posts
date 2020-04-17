package com.jesuslcorominas.posts.testshared

import com.jesuslcorominas.posts.domain.Author
import com.jesuslcorominas.posts.domain.Comment
import com.jesuslcorominas.posts.domain.Post

val mockedPost = Post(
    0,
    1,
    "title",
    "body",
    null,
    ArrayList()
)

val mockedAuthor = Author(1, "Author", "email@author.com")
val mockedComment = Comment(1, 2, "Comment name", "jesuslcorominas@gmail.com", "Comment body")
