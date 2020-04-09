package com.jesuslcorominas.posts.app.ui.main

import com.jesuslcorominas.posts.usecases.GetPostUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertNull
import org.junit.Test

class MainViewModelTest {

    private val getPostUseCase: GetPostUseCase = mock()
    private val mainViewModel = MainViewModel(getPostUseCase)

    @Test
    fun `posts list should be null when viewmodel is initialized`() {
        assertNull(mainViewModel.items.value)
    }

    @Test
    fun `init viewmodel should call getPosts use case`() {
        verify(getPostUseCase).getPosts()
    }
}