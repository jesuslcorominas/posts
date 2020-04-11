package com.jesuslcorominas.posts.app.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.posts.app.ui.detail.DetailViewModel
import com.jesuslcorominas.posts.app.ui.main.MainViewModel
import com.jesuslcorominas.posts.usecases.GetPostUseCase

/**
 * Factory for the three ViewModel of this project
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val getPostUseCase: GetPostUseCase) : ViewModelProvider.Factory {

    // TODO hay que pasar el id
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(getPostUseCase) as T
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(0) as T
            else -> throw IllegalArgumentException("ViewModel ${modelClass.name} not implemented in this factory")
        }
    }
}