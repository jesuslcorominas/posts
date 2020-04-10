package com.jesuslcorominas.posts.app.ui.main

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.posts.domain.Post


@BindingAdapter("items")
fun RecyclerView.setItems(posts: List<Post>?) {
    (adapter as? PostAdapter)?.let {
        it.items = posts ?: emptyList()
    }
}