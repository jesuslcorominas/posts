package com.jesuslcorominas.posts.usecases

import com.jesuslcorominas.posts.data.repository.PostsRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

class GetPostsUseCaseTest {

    val postsRepository: PostsRepository = mock()

    val getPostsUseCase = GetPostsUseCase(postsRepository)

    @Test
    fun `get posts should call repository get posts`() {
        getPostsUseCase.getPosts()

        verify(postsRepository).getPosts()
    }
}