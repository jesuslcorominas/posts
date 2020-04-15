package com.jesuslcorominas.posts.app.data.remote.datasource

import com.jesuslcorominas.posts.app.data.remote.service.RemoteApi
import com.jesuslcorominas.posts.app.data.remote.service.RemoteService
import com.jesuslcorominas.posts.app.data.remote.service.toRemotePost
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.ServerException
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.observers.TestObserver
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import com.jesuslcorominas.posts.app.data.remote.service.Post as RemotePost
import com.jesuslcorominas.posts.domain.Post as DomainPost


class RemoteDatasourceTest {

    private val remoteService: RemoteService = mock()

    private val remoteDatasource: RemoteDatasource = RemoteDatasourceImpl(remoteService)

    @Before
    fun setUp() {
        val mockedRemoteApi: RemoteApi = mock()
        whenever(remoteService.api()).thenReturn(mockedRemoteApi)

        val mockedCall: Call<List<RemotePost>> = mock()
        whenever(mockedRemoteApi.getPosts()).thenReturn(mockedCall)
    }

    @Test
    fun `getPosts should get remote posts`() {
        val mockedRemotePosts = listOf(mockedPost.copy(1))
        whenever(remoteService.api().getPosts().execute()).thenReturn(
            Response.success(
                mockedRemotePosts.map { it.toRemotePost() }
            )
        )

        val testObserver: TestObserver<List<DomainPost>> = remoteDatasource.getPosts().test()
        testObserver.assertValue { it == mockedRemotePosts }

        testObserver.dispose()
    }

    @Test
    fun `if getPosts fails ConnectionException must be thrown`() {
        whenever(remoteService.api().getPosts().execute()).thenThrow(IOException())

        val testObserver: TestObserver<List<DomainPost>> = remoteDatasource.getPosts().test()
        testObserver.assertError(ConnectionException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `if getPosts is not successful ServerException must be emmited`() {
        // TODO revisar este test.
        val responseBody: ResponseBody = mock()

        whenever(remoteService.api().getPosts().execute())
            .thenReturn(Response.error(responseBody, mock()))

        val testObserver: TestObserver<List<DomainPost>> = remoteDatasource.getPosts().test()
        testObserver.assertError(ServerException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `if getPosts responses has no body InvalidResponseException must be emmited`() {
        whenever(remoteService.api().getPosts().execute())
            .thenReturn(
                Response.success(null)
            )

        val testObserver: TestObserver<List<DomainPost>> = remoteDatasource.getPosts().test()
        testObserver.assertError(InvalidResponseException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `get post detail should get author from remote`() {

    }
}