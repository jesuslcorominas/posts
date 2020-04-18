package com.jesuslcorominas.posts.data.repository

import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.DatabaseException
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.domain.RemoteException
import com.jesuslcorominas.posts.testshared.mockedAuthor
import com.jesuslcorominas.posts.testshared.mockedComment
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test

class PostRepositoryTest {

    private val localDatasource: LocalDatasource = mock()
    private val remoteDatasource: RemoteDatasource = mock()

    private val postId = 1

    private val postsRepository = PostRepository(localDatasource, remoteDatasource)

    // region GetPosts

    @Test
    fun `get posts should call remote datasource`() {
        val posts = listOf(mockedPost.copy(postId))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(posts) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(posts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(remoteDatasource).getPosts()

        testObserver.dispose()
    }

    @Test
    fun `when remote getPosts return error local datasource should be called`() {
        val posts = listOf(mockedPost.copy(postId))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(posts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(localDatasource).getPosts()

        testObserver.dispose()
    }

    @Test
    fun `when remote getPosts return error local posts should be retrieved`() {
        val posts = listOf(mockedPost.copy(postId))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onError(Exception()) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(posts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        testObserver.assertValue { it == posts }

        testObserver.dispose()
    }

    @Test
    fun `when remote posts retrieved its should be saved on local`() {
        val posts = listOf(mockedPost.copy(postId))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(posts) })
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onSuccess(posts) })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        verify(localDatasource).savePosts(posts)

        testObserver.dispose()
    }

    /**
     * Creamos el post localPost con un id distinto para asegurarnos de que los posts que finalmente
     * se devuelven son los locales
     */
    @Test
    fun `when remote posts retrieved local post should be retrieved`() {
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
    fun `when remote get posts returns posts and no local post retrieved DatabaseException should be thrown`() {
        val posts = listOf(mockedPost.copy(postId))

        whenever(remoteDatasource.getPosts()).thenReturn(Single.create { it.onSuccess(posts) })
        whenever(localDatasource.savePosts(posts)).thenReturn(Completable.complete())
        whenever(localDatasource.getPosts()).thenReturn(Maybe.create { it.onComplete() })

        val testObserver: TestObserver<List<Post>> = postsRepository.getPosts().test()

        testObserver.assertError(DatabaseException::class.java)

        testObserver.dispose()
    }

    // endregion

    // region Post Detail
    @Test
    fun `getPostDetail should find post by id`() {
        val post = mockedPost.copy(postId)

        whenever(localDatasource.findPostById(any())).thenReturn(Single.create {
            it.onSuccess(post)
        })

        postsRepository.getPostDetail(postId)

        verify(localDatasource).findPostById(postId)
    }

    @Test
    fun `if post not found on database DatabaseException should be thrown`() {
        whenever(localDatasource.findPostById(any())).thenReturn(Single.error(Exception()))
        whenever(remoteDatasource.getPostDetail(any())).thenReturn(Single.error(Exception()))
        whenever(localDatasource.getPostDetail(any())).thenReturn(Maybe.create { it.onComplete() })

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        testObserver.assertError(DatabaseException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when local post found remote post should be retrieved`() {
        val post = mockedPost.copy(postId)
        val postWithDetail = post.copy(author = mockedAuthor, comments = listOf(mockedComment))

        whenever(localDatasource.findPostById(postId)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(remoteDatasource.getPostDetail(post)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(localDatasource.getPostDetail(postId)).thenReturn(Maybe.create {
            it.onSuccess(postWithDetail)
        })
        whenever(localDatasource.savePosts(any())).thenReturn(Completable.complete())

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        verify(remoteDatasource).getPostDetail(post)

        testObserver.dispose()
    }

    @Test
    fun `when get remote post fails local post should be retrieved`() {
        val post = mockedPost.copy(postId)
        val postWithDetail = post.copy(author = mockedAuthor, comments = listOf(mockedComment))

        whenever(localDatasource.findPostById(postId)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(remoteDatasource.getPostDetail(post)).thenReturn(
            Single.error(ConnectionException())
        )
        whenever(localDatasource.getPostDetail(postId)).thenReturn(Maybe.create {
            it.onSuccess(postWithDetail)
        })
        whenever(localDatasource.savePosts(any())).thenReturn(Completable.complete())

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        testObserver.assertValue { it == postWithDetail }

        testObserver.dispose()
    }

    @Test
    fun `when get remote and local post fails exception should be thrown`() {
        val post = mockedPost.copy(postId)

        whenever(localDatasource.findPostById(postId)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(remoteDatasource.getPostDetail(post)).thenReturn(
            Single.error(ConnectionException())
        )
        whenever(localDatasource.getPostDetail(postId)).thenReturn(
            Maybe.create { emitter -> emitter.onComplete() }
        )

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        testObserver.assertError(RemoteException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when remote post retrieved it should be saved on database`() {
        val post = mockedPost.copy(postId)
        val postWithDetail = post.copy(author = mockedAuthor, comments = listOf(mockedComment))

        whenever(localDatasource.findPostById(postId)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(remoteDatasource.getPostDetail(post)).thenReturn(Single.create {
            it.onSuccess(postWithDetail)
        })
        whenever(localDatasource.getPostDetail(postId)).thenReturn(Maybe.create {
            it.onSuccess(postWithDetail)
        })
        whenever(localDatasource.savePosts(any())).thenReturn(Completable.complete())

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        verify(localDatasource).savePosts(listOf(postWithDetail))

        testObserver.dispose()
    }

    /**
     * Creamos el post localPostWithDetail con un id distinto para asegurarnos de que el post que se
     * devuelve es el de la base de datos
     */
    @Test
    fun `getPostDetail should retrieve local post`() {
        val post = mockedPost.copy(postId)
        val remotePostWithDetail =
            post.copy(author = mockedAuthor, comments = listOf(mockedComment))
        val localPostWithDetail =
            post.copy(id = 2, author = mockedAuthor, comments = listOf(mockedComment))

        whenever(localDatasource.findPostById(postId)).thenReturn(Single.create {
            it.onSuccess(post)
        })
        whenever(remoteDatasource.getPostDetail(post)).thenReturn(Single.create {
            it.onSuccess(remotePostWithDetail)
        })
        whenever(localDatasource.getPostDetail(postId)).thenReturn(Maybe.create {
            it.onSuccess(localPostWithDetail)
        })
        whenever(localDatasource.savePosts(any())).thenReturn(Completable.complete())

        val testObserver: TestObserver<Post> = postsRepository.getPostDetail(postId).test()
        testObserver.assertValue { it == localPostWithDetail }

        testObserver.dispose()
    }

    // endregion
}
