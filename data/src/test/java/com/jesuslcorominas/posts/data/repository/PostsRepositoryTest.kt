package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test

class PostsRepositoryTest {

    private val localDatasource: LocalDatasource = mock()
    private val remoteDatasource: RemoteDatasource = mock()

    private val postsRepository = PostsRepository(localDatasource, remoteDatasource)

    @Test
    fun `get posts should check if local datasource is empty`() {
        postsRepository.getPosts()

        verify(localDatasource).isEmpty()
    }

    @Test
    fun `when local datasource is empty should get posts from remote datasource`() {
        whenever(localDatasource.isEmpty()).thenReturn(true)

        postsRepository.getPosts()

        verify(remoteDatasource).getPosts()
    }

    @Test
    fun `when local datasource is not empty remote datasource should not be called`() {
        whenever(localDatasource.isEmpty()).thenReturn(false)

        postsRepository.getPosts()

        verifyZeroInteractions(remoteDatasource)
    }

    @Test
    fun `when remote posts list retrieved its should be saved on local datasource`() {

    }

    @Test
    fun `when local datasource is not empty local posts should be retrieved`() {
        val mockedLocalPosts = listOf(mockedPost.copy(1))

        whenever(localDatasource.isEmpty()).thenReturn(false)
        whenever(localDatasource.getPosts()).thenReturn(mockedLocalPosts)

        val posts = postsRepository.getPosts()

        assertEquals(mockedLocalPosts, posts)
    }
}