package com.jesuslcorominas.posts.app.ui.main

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.ItemPostBinding
import com.jesuslcorominas.posts.app.ui.common.basicDiffUtil
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import com.jesuslcorominas.posts.domain.Post

class PostAdapter(private val listener: (Post) -> Unit) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    var items: List<Post> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.item_post, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = items[position]
        holder.dataBinding.post = post

        holder.itemView.setOnClickListener { listener(post) }
    }

    class ViewHolder(val dataBinding: ItemPostBinding) : RecyclerView.ViewHolder(dataBinding.root)
}