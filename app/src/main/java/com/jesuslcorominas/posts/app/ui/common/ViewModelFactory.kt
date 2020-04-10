package com.jesuslcorominas.posts.app.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jesuslcorominas.posts.app.ui.main.MainViewModel
import com.jesuslcorominas.posts.usecases.GetPostUseCase

/**
 * Factory for the three ViewModel of this project
 */
class ViewModelFactory(private val getPostUseCase: GetPostUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(getPostUseCase) as T
            else -> throw IllegalArgumentException("ViewModel ${modelClass.name} not implemented in this factory")
        }
    }
}