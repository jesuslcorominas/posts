package com.jesuslcorominas.posts.app.ui.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.ItemCommentBinding
import com.jesuslcorominas.posts.app.ui.common.basicDiffUtil
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import com.jesuslcorominas.posts.domain.Comment

class CommentAdapter :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var items: List<Comment> by basicDiffUtil(
        emptyList(),
        areItemsTheSame = { old, new -> old.id == new.id }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.bindingInflate(R.layout.item_comment, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = items[position]
        holder.dataBinding.comment = comment
    }

    class ViewHolder(val dataBinding: ItemCommentBinding) :
        RecyclerView.ViewHolder(dataBinding.root)
}