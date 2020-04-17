package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.DatabaseException
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.any
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
    fun `when remote getPosts return error local datasource should be called`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(localDatasource).getPosts()

        testObserver.dispose()
    }

    @Test
    fun `when remote getPosts return error local posts should be retrived`() {
        val mockedPosts = listOf(mockedPost.copy(1))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(mockedPosts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        testObserver.assertValue { it == mockedPosts }

        testObserver.dispose()
    }

    @Test
    fun `when remote posts retrived its should be saved on local`() {
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
    fun `when remote posts retrived local post should be retrived`() {
        val remotePost = listOf(mockedPost.copy(1))
        val localPost = listOf(mockedPost.copy(2))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(remotePost) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(localPost) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()
        testObserver.assertValue { it == localPost }

        testObserver.dispose()
    }

    @Test
    fun `when remote datasource return error and no posts stored remote exception should be thrown`() {
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

    @Test
    fun `getPostDetail should find post by id`() {
        val mockedPost = mockedPost.copy(1)

        whenever(localDatasource.findPostById(any())).thenReturn(Single.create {
            it.onSuccess(mockedPost)
        })

        postsRepository.getPostDetail(1)

        verify(localDatasource).findPostById(1)
    }

    @Test
    fun `if post not found on database DatabaseException should be thrown`() {
        whenever(localDatasource.findPostById(any())).thenReturn(Single.error(Exception()))
        whenever(remoteDatasource.getPostDetail(any())).thenReturn(Single.error(Exception()))
        whenever(localDatasource.getPostDetail(any())).thenReturn(Maybe.create { it.onComplete() })

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(1).test()
        testObserver.assertError(DatabaseException::class.java)

        testObserver.dispose()
    }

    // TODO revisar estos tests
    @Test
    fun `when local post found remote post should be retrived`() {
        val mockedPost = mockedPost.copy(1)

        whenever(localDatasource.findPostById(1)).thenReturn(Single.create {
            it.onSuccess(mockedPost)
        })
        whenever(remoteDatasource.getPostDetail(mockedPost)).thenReturn(Single.create {
            it.onSuccess(mockedPost)
        })
        whenever(localDatasource.getPostDetail(any())).thenReturn(Maybe.create {
            it.onSuccess(mockedPost)
        })

        postsRepository.getPostDetail(1)

        verify(remoteDatasource).getPostDetail(mockedPost)
    }

    @Test
    fun `when get remote post fails local post should be retrived`() {

    }

    @Test
    fun `when get remote and local post fails exception should be thrown`() {

    }

    @Test
    fun `when remote post retrived it should be saved on database`() {

    }

    @Test
    fun `getPostDetail should retrive local post`() {

    }
}
