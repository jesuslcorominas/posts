package com.jesuslcorominas.posts.domain

data class Post(val id: Int, val userId: Int, val title: String, val body: String) {
    companion object {
        const val THUMBNAIL_WIDTH: Int = 128
        const val THUMBNAIL_HEIGHT: Int = 128

        const val IMAGE_WIDTH: Int = 1280
        const val IMAGE_HEIGHT: Int = 720

        const val THUMBNAIL_URL =
            "https://i.picsum.photos/id/%d/$THUMBNAIL_WIDTH/$THUMBNAIL_HEIGHT.jpg"
    }
}

data class Author(
    val id: Int,
    val name: String,
    val userName: String,
    val email: String
)

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)
