package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.PostDao
import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.database.toDbPost
import com.jesuslcorominas.posts.data.source.LocalDatasource
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.testshared.mockedPost
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
    fun `when isEmpty called postCount should be invoked`() {
        localDatasource.isEmpty()

        verify(postDatabase.postDao()).postCount()
    }

    @Test
    fun `when no posts stored isEmpty should emmit true`() {
        whenever(postDatabase.postDao().postCount()).thenReturn(0)

        val testObserver: TestObserver<Boolean> = localDatasource.isEmpty().test()
        testObserver.assertValue { it }

        testObserver.dispose()
    }

    @Test
    fun `when some post stored isEmpty should return false`() {
        whenever(postDatabase.postDao().postCount()).thenReturn(1)

        val testObserver: TestObserver<Boolean> = localDatasource.isEmpty().test()
        testObserver.assertValue { !it }

        testObserver.dispose()
    }

    @Test
    fun `getPosts should retrieve stored posts`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        whenever(postDatabase.postDao().getAll()).thenReturn(mockStoredPosts.map { it.toDbPost() })

        val testObserver: TestObserver<List<Post>> = localDatasource.getPosts().test()
        testObserver.assertValue { it == mockStoredPosts }

        testObserver.dispose()
    }

    @Test
    fun `savePosts should insert post list`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        localDatasource.savePosts(mockStoredPosts)

        verify(postDatabase.postDao()).insertPosts(mockStoredPosts.map { it.toDbPost() })
    }
}