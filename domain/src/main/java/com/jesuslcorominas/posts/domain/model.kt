package com.jesuslcorominas.posts.domain

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val author: Author?,
    val comments: List<Comment>
) {
    companion object {
        private const val THUMBNAIL_WIDTH: Int = 128
        private const val THUMBNAIL_HEIGHT: Int = 128

        private const val PICTURE_WIDTH: Int = 1280
        private const val PICTURE_HEIGHT: Int = 720

        private const val IMAGE_URL = "https://i.picsum.photos/id/%d/"
        private const val IMAGE_EXTENSION = ".jpg"

        const val THUMBNAIL_URL =
            "$IMAGE_URL$THUMBNAIL_WIDTH/$THUMBNAIL_HEIGHT$IMAGE_EXTENSION"

        const val PICTURE_URL =
            "$IMAGE_URL$PICTURE_WIDTH/$PICTURE_HEIGHT$IMAGE_EXTENSION"
    }
}

data class Author(val id: Int, val name: String, val email: String)

data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
) {
    companion object {
        private const val AVATAR_SIZE: Int = 48

        private const val IMAGE_URL = "https://api.adorable.io/avatars/"

        const val AVATAR_URL =
            "${IMAGE_URL}${AVATAR_SIZE}/%s"
    }
}
