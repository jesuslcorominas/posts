package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class GetPostUseCaseTest {

    val postRepository: PostRepository = mock()

    val getPostsUseCase = GetPostUseCase(postRepository)

    @Test
    fun `get posts should call posts repository`() {
        getPostsUseCase.getPosts()

        verify(postRepository).getPosts()
    }
}