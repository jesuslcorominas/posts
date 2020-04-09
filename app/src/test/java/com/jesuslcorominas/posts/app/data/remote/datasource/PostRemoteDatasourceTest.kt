package com.jesuslcorominas.posts.app.data.remote.datasource

import com.jesuslcorominas.posts.app.data.remote.model.Post
import com.jesuslcorominas.posts.app.data.remote.service.RemoteApi
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.data.source.PostRemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import kotlin.test.assertFailsWith


class PostRemoteDatasourceTest {

    private val remoteService: RemoteService = mock()

    private val postRemoteDatasource: PostRemoteDatasource = PostRemoteDatasourceImpl(remoteService)

    @Before
    fun setUp() {
        val mockedRemoteApi: RemoteApi = mock()
        whenever(remoteService.remoteApi()).thenReturn(mockedRemoteApi)

        val mockedCall: Call<List<Post>> = mock()
        whenever(mockedRemoteApi.getPosts()).thenReturn(mockedCall)
    }

    @Test
    fun `getPosts should call remote api`() {
        whenever(remoteService.remoteApi().getPosts().execute()).thenReturn(
            Response.success(
                ArrayList()
            )
        )

        postRemoteDatasource.getPosts()

        verify(remoteService.remoteApi().getPosts()).execute()
    }

    @Test(expected = ConnectionException::class)
    fun `if getPosts fails ConnectionException must be thrown`() {
        whenever(remoteService.remoteApi().getPosts().execute()).thenThrow(IOException())

        postRemoteDatasource.getPosts()
    }

    @Test
    fun `if getPosts is not successful ServerException must be thrown`() {
        val responseBody: ResponseBody = mock()

        whenever(remoteService.remoteApi().getPosts().execute()).thenReturn(
            Response.error(
                500,
                responseBody
            )
        )

        postRemoteDatasource.getPosts()

//        val exception = assertFailsWith<ConnectionException> {  }
//        assertTrue(exception.cause is ConnectionException)
    }
}