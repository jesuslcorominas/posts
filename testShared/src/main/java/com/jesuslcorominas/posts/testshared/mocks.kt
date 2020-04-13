package com.jesuslcorominas.posts.testshared

import com.jesuslcorominas.posts.domain.Comment
import com.jesuslcorominas.posts.domain.Post

val mockedPost = Post(
    0,
    1,
    "title",
    "body",
    "author",
    listOf(Comment(1, 2, "Comment name", "jesuslcorominas@gmail.com", "Comment body"))
)