package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.database.dao.PostDao
import com.jesuslcorominas.posts.app.data.local.database.toDbPost
import com.jesuslcorominas.posts.app.data.local.database.toDbPostDetail
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedAuthor
import com.jesuslcorominas.posts.testshared.mockedComment
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test

class LocalDatasourceTest {

    private val postDatabase: PostDatabase = mock()

    private val localDatasource: LocalDatasource = LocalDatasourceImpl(postDatabase)

    @Before
    fun setUp() {
        val postDao: PostDao = mock()
        whenever(postDatabase.postDao()).thenReturn(postDao)
    }

    @Test
    fun `if posts is empty getPost should get no post`() {
        whenever(postDatabase.postDao().getAll()).thenReturn(ArrayList())

        val testObserver: TestObserver<List<Post>> = localDatasource.getPosts().test()
        testObserver.assertComplete()

        testObserver.dispose()
    }

    @Test
    fun `if posts has items getPosts should retrieve stored posts`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        whenever(postDatabase.postDao().getAll()).thenReturn(mockStoredPosts.map { it.toDbPost() })

        val testObserver: TestObserver<List<Post>> = localDatasource.getPosts().test()
        testObserver.assertValue { it == mockStoredPosts }

        testObserver.dispose()
    }

    @Test
    fun `findPostById should retrieve post with specific id`() {
        val mockStoredPost = mockedPost.copy(1)

        whenever(
            postDatabase
                .postDao()
                .findPostById(1)
        )
            .thenReturn(mockStoredPost.toDbPost())

        val testObserver: TestObserver<Post> = localDatasource.findPostById(1).test()
        testObserver.assertValue { it == mockStoredPost }

        testObserver.dispose()
    }

    @Test
    fun `when posts author is null getPostDetail should not get post`() {
        val mockStoredPostWithOutAuthor = mockedPost.copy(1)

        whenever(
            postDatabase
                .postDao()
                .getPostWithComments(1)
        )
            .thenReturn(mockStoredPostWithOutAuthor.toDbPostDetail())


        val testObserver: TestObserver<Post> = localDatasource.getPostDetail(1).test()
        testObserver.assertComplete()

        testObserver.dispose()
    }

    @Test
    fun `getPostWithComments should retrieve post with author and comments`() {
        val mockStoredPostWithDetail = mockedPost.copy(
            1,
            author = mockedAuthor.copy(1),
            comments = listOf(mockedComment.copy(1))
        )

        whenever(
            postDatabase
                .postDao()
                .getPostWithComments(1)
        )
            .thenReturn(mockStoredPostWithDetail.toDbPostDetail())

        val testObserver: TestObserver<Post> = localDatasource.getPostDetail(1).test()
        testObserver.assertValue { it == mockStoredPostWithDetail }

        testObserver.dispose()
    }

    @Test
    fun `savePosts should run an insert transaction`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        localDatasource.savePosts(mockStoredPosts)

        verify(postDatabase).runInTransaction(any())
    }
}