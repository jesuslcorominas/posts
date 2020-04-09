package com.jesuslcorominas.posts.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.usecases.GetPostsUseCase


class MainViewModel(private val getPostsUseCase: GetPostsUseCase) : ViewModel() {
    private val _items: MutableLiveData<Post> = MutableLiveData()
    val items: LiveData<Post> get() = _items

    init {
        getPosts()
    }

    fun getPosts() {
        getPostsUseCase.getPosts()
    }

}
