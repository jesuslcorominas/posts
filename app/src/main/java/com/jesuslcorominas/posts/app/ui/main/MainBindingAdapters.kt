package com.jesuslcorominas.posts.app.ui.main

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.ui.common.loadUrl
import com.jesuslcorominas.posts.app.ui.common.setResourceText
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.domain.ServerException


@BindingAdapter("items")
fun RecyclerView.setItems(posts: List<Post>?) {
    (adapter as? PostAdapter)?.let {
        it.items = posts ?: emptyList()
    }
}

@BindingAdapter("error")
fun TextView.setError(e: Throwable) {
    when (e) {
        is ConnectionException -> setResourceText(R.string.get_post_error_message)
        is InvalidResponseException -> setResourceText(R.string.get_post_error_message)
        is ServerException -> setResourceText(R.string.get_post_error_message)
        else -> setResourceText(R.string.unknown_error_message)
    }
}

@BindingAdapter("thumbnail")
fun ImageView.loadThumbnail(postId: Int?) {
    if (postId != null) {
        loadUrl(String.format(Post.THUMBNAIL_URL, postId))
    } else {
        setImageResource(R.drawable.ic_photo)
    }
}

