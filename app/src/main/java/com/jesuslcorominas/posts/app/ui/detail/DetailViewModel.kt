package com.jesuslcorominas.posts.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.posts.app.ui.common.BaseViewModel
import com.jesuslcorominas.posts.domain.Post

class DetailViewModel(
    private val postId: Int
) : BaseViewModel() {

    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> get() = _post

    init {
        getPostDetail()
    }

    fun getPostDetail() {
        _loading.value = true
        hideError()
    }

}
