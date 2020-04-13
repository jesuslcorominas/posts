package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test

class PostRepositoryTest {

    private val localDatasource: LocalDatasource = mock()
    private val remoteDatasource: RemoteDatasource = mock()

    private val postsRepository = PostRepository(localDatasource, remoteDatasource)

    @Test
    fun `get posts should check if local datasource is empty`() {
        postsRepository.getPosts()

        verify(localDatasource).isEmpty()
    }

    @Test
    fun `when local datasource is empty should get posts from remote datasource`() {
        whenever(localDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(true) })

        postsRepository.getPosts()

        verify(remoteDatasource).getPosts()
    }

    @Test
    fun `when remote posts list retrieved its should be saved to local`() {
        val mockedRemotePosts = listOf(mockedPost.copy(1))

        whenever(localDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(true) })
        whenever(remoteDatasource.getPosts()).thenReturn(Single.create {
            it.onSuccess(
                mockedRemotePosts
            )
        })

        postsRepository.getPosts()

        verify(localDatasource).savePosts(mockedRemotePosts)
    }


    @Test
    fun `when local datasource is not empty remote datasource should not be called`() {
        whenever(localDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(false) })

        postsRepository.getPosts()

        verifyZeroInteractions(remoteDatasource)
    }


    @Test
    fun `when local datasource is not empty local posts should be retrieved`() {
        val mockedLocalPosts = listOf(mockedPost.copy(1))

        whenever(localDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(false) })
        whenever(localDatasource.getPosts()).thenReturn(Single.create {
            it.onSuccess(
                mockedLocalPosts
            )
        })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()
        testObserver.assertValue { it == mockedLocalPosts }

        testObserver.dispose()
    }
}