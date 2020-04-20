package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class GetPostDetailUseCaseTest {

    val postRepository: PostRepository = mock()

    val getPostDetailUseCase = GetPostDetailUseCase(postRepository)

    @Test
    fun `get posts should call posts repository`() {
        getPostDetailUseCase.getPostDetail(any())

        verify(postRepository).getPostDetail(any())
    }
}