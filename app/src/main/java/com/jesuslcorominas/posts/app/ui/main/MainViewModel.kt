package com.jesuslcorominas.posts.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.usecases.GetPostUseCase


class MainViewModel(private val getPostUseCase: GetPostUseCase) : ViewModel() {
    private val _items: MutableLiveData<Post> = MutableLiveData()
    val items: LiveData<Post> get() = _items

    init {
        getPosts()
    }

    fun getPosts() {
        getPostUseCase.getPosts()
    }

}
