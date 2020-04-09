package com.jesuslcorominas.posts.app.data.local.datasource

import com.jesuslcorominas.posts.app.data.local.database.PostDao
import com.jesuslcorominas.posts.app.data.local.database.PostDatabase
import com.jesuslcorominas.posts.app.data.local.model.toDbPost
import com.jesuslcorominas.posts.data.source.PostLocalDatasource
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostLocalDatasourceTest {

    private val postDatabase: PostDatabase = mock()

    private val postLocalDatasource: PostLocalDatasource = PostLocalDatasourceImpl(postDatabase)

    @Before
    fun setUp() {
        val postDao: PostDao = mock()
        whenever(postDatabase.postDao()).thenReturn(postDao)
    }

    @Test
    fun `when isEmpty called postCount should be invoked`() {
        postLocalDatasource.isEmpty()

        verify(postDatabase.postDao()).postCount()
    }

    @Test
    fun `when no posts stored isEmpty should return true`() {
        whenever(postDatabase.postDao().postCount()).thenReturn(0)

        assertTrue(postLocalDatasource.isEmpty())
    }

    @Test
    fun `when some post stored isEmpty should return false`() {
        whenever(postDatabase.postDao().postCount()).thenReturn(1)

        assertFalse(postLocalDatasource.isEmpty())
    }

    @Test
    fun `getPosts should retrieve stored posts`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        whenever(postDatabase.postDao().getAll()).thenReturn(mockStoredPosts.map { it.toDbPost() })

        val storedPost = postLocalDatasource.getPosts()

        assertEquals(storedPost, mockStoredPosts)
    }

    @Test
    fun `savePosts should insert post list`() {
        val mockStoredPosts = listOf(mockedPost.copy(1))

        postLocalDatasource.savePosts(mockStoredPosts)

        verify(postDatabase.postDao()).insertPosts(mockStoredPosts.map { it.toDbPost() })
    }
}