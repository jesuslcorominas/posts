package com.jesuslcorominas.posts.app.ui.detail

import com.jesuslcorominas.posts.app.ui.common.BaseViewModel

class DetailViewModel(
    private val postId: Int
) : BaseViewModel() {

    init {
        getPostDetail()
    }

    fun getPostDetail() {
        _loading.value = true
        hideError()

        TODO("Obtener el detalle")
    }

}
