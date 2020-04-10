package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Test

class PostRepositoryTest {

    private val postLocalDatasource: PostLocalDatasource = mock()
    private val postRemoteDatasource: PostRemoteDatasource = mock()

    private val postsRepository = PostRepository(postLocalDatasource, postRemoteDatasource)

    @Test
    fun `get posts should check if local datasource is empty`() {
        postsRepository.getPosts()

        verify(postLocalDatasource).isEmpty()
    }

    @Test
    fun `when local datasource is empty should get posts from remote datasource`() {
        whenever(postLocalDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(true) })

        postsRepository.getPosts()

        verify(postRemoteDatasource).getPosts()
    }

    @Test
    fun `when remote posts list retrieved its should be saved to local`() {
        val mockedRemotePosts = listOf(mockedPost.copy(1))

        whenever(postLocalDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(true) })
        whenever(postRemoteDatasource.getPosts()).thenReturn(Single.create {
            it.onSuccess(
                mockedRemotePosts
            )
        })

        postsRepository.getPosts()

        verify(postLocalDatasource).savePosts(mockedRemotePosts)
    }


    @Test
    fun `when local datasource is not empty remote datasource should not be called`() {
        whenever(postLocalDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(false) })

        postsRepository.getPosts()

        verifyZeroInteractions(postRemoteDatasource)
    }


    @Test
    fun `when local datasource is not empty local posts should be retrieved`() {
        val mockedLocalPosts = listOf(mockedPost.copy(1))

        whenever(postLocalDatasource.isEmpty()).thenReturn(Single.create { it.onSuccess(false) })
        whenever(postLocalDatasource.getPosts()).thenReturn(Single.create {
            it.onSuccess(
                mockedLocalPosts
            )
        })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()
        testObserver.assertValue { it == mockedLocalPosts }

        testObserver.dispose()
    }
}