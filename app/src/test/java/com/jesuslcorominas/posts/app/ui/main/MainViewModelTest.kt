package com.jesuslcorominas.posts.app.ui.main

import com.jesuslcorominas.posts.usecases.GetPostsUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNull
import org.junit.Test

class MainViewModelTest {

    private val getPostsUseCase: GetPostsUseCase = mock()
    private val mainViewModel = MainViewModel(getPostsUseCase)

    @Test
    fun `posts list should be null when viewmodel is initialized`() {
        assertNull(mainViewModel.items.value)
    }

    @Test
    fun `init viewmodel should be call getPosts use case`() {
        verify(getPostsUseCase).getPosts()
    }
}