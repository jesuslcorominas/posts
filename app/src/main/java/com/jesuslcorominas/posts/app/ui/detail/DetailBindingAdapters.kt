package com.jesuslcorominas.posts.app.ui.detail

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.ui.common.loadUrl
import com.jesuslcorominas.posts.domain.Author
import com.jesuslcorominas.posts.domain.Comment
import com.jesuslcorominas.posts.domain.Post

@BindingAdapter("items")
fun RecyclerView.setItems(comments: List<Comment>?) {
    (adapter as? CommentAdapter)?.let {
        it.items = comments ?: emptyList()
    }
}

@BindingAdapter("picture")
fun ImageView.setPicture(postId: Int?) {
    if (postId != null) {
        loadUrl(String.format(Post.PICTURE_URL, postId))
    } else {
        setImageResource(R.drawable.ic_photo)
    }
}

@BindingAdapter("comments_count")
fun TextView.setCommentsCount(commentsCount: Int?) {
    if (commentsCount != null) {
        if (commentsCount == 1) {
            text = context.getString(R.string.one_comment_count)
        } else {
            text = context.getString(R.string.comments_count, commentsCount)
        }
    } else {
        text = context.getString(R.string.comments_count, 0)
    }
}

@BindingAdapter("author")
fun TextView.setAuthor(author: Author?) {
    if (author != null) {
        text = with(author) {
            "$name ($email)"
        }
    } else {
        text = ""
    }
}

@BindingAdapter("email")
fun EmojiTextView.setEmail(email: String?) {
    if (email != null) {
        text = email.formatEmailWithEmojis()
    } else {
        text = null
    }
}

private fun String.formatEmailWithEmojis(): String {
    if (endsWith(Author.END_INFO)) {
        return "$this  ${Author.INFO}"
    } else if (endsWith(Author.END_UK)) {
        return "$this  ${Author.UK}"
    }

    return this
}

@BindingAdapter("avatar")
fun ImageView.setAvatar(email: String?) {
    if (email != null) {
        loadUrl(String.format(Comment.AVATAR_URL, email))
    } else {
        setImageResource(R.drawable.ic_android)
    }
}