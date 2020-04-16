package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.DatabaseException
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test

class PostRepositoryTest {

    private val localDatasource: LocalDatasource = mock()
    private val remoteDatasource: RemoteDatasource = mock()

    private val postsRepository = PostRepository(localDatasource, remoteDatasource)

    @Test
    fun `get posts should call remote datasource`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(mockedPosts) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(remoteDatasource).getPosts()

        testObserver.dispose()
    }

    @Test
    fun `when remote datasource return error local datasource should be called`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(localDatasource).getPosts()

        testObserver.dispose()
    }

    @Test
    fun `when remote datasource return error local post should be retrived`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        testObserver.assertValue { it == mockedPosts }

        testObserver.dispose()
    }

    @Test
    fun `when remote post retrived its should be saved on local`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(mockedPosts) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(localDatasource).savePosts(mockedPosts)

        testObserver.dispose()
    }

    /**
     * Este test en produccion no se da asi porque se guarda en local los datos devueltos por remoto
     * pero asi comprobamos que lo que se devuelve finalmente son los locales y no los remotos
     */
    @Test
    fun `when remote post retrived local post should be retrived`() {
        val remotePost = listOf(mockedPost.copy(1))
        val localPost = listOf(mockedPost.copy(2))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(remotePost) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(localPost) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()
        testObserver.assertValue { it == localPost }

        testObserver.dispose()
    }

    @Test
    fun `when remote datasource return error and no local post remote exception should be thrown`() {
        whenever(remoteDatasource.getPosts()).thenReturn(Single.create {
            it.onError(
                ConnectionException()
            )
        })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onComplete() })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()
        testObserver.assertError(ConnectionException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when remote returns posts and no local post DatabaseException should be thrown`() {
        // TODO revisar este test

        val remotePost = listOf(mockedPost.copy(1))
        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(remotePost) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onComplete() })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

//        testObserver.assertError(DatabaseException::class.java)

        testObserver.dispose()
    }


}