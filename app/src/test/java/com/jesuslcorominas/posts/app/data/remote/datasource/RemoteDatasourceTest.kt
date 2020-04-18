package com.jesuslcorominas.posts.app.data.remote.datasource

import com.jesuslcorominas.posts.app.data.remote.service.*
import com.jesuslcorominas.posts.data.source.RemoteDatasource
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.ServerException
import com.jesuslcorominas.posts.testshared.mockedAuthor
import com.jesuslcorominas.posts.testshared.mockedComment
import com.jesuslcorominas.posts.testshared.mockedPost
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.observers.TestObserver
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import com.jesuslcorominas.posts.app.data.remote.service.Author as RemoteAuthor
import com.jesuslcorominas.posts.app.data.remote.service.Comment as RemoteComment
import com.jesuslcorominas.posts.app.data.remote.service.Post as RemotePost
import com.jesuslcorominas.posts.domain.Post as DomainPost


class RemoteDatasourceTest {

    private val remoteService: RemoteService = mock()

    private val remoteDatasource: RemoteDatasource = RemoteDatasourceImpl(remoteService)

    @Before
    fun setUp() {
        val remoteApi: RemoteApi = mock()
        whenever(remoteService.api()).thenReturn(remoteApi)

        val mockedListCall: Call<List<RemotePost>> = mock()
        whenever(remoteApi.getPosts()).thenReturn(mockedListCall)

        val mockedAuthorCall: Call<RemoteAuthor> = mock()
        whenever(remoteApi.getAuthor(any())).thenReturn(mockedAuthorCall)

        val mockedCommentsCall: Call<List<RemoteComment>> = mock()
        whenever(remoteApi.getComments(any())).thenReturn(mockedCommentsCall)
    }

    // region Posts

    @Test
    fun `getPosts should get remote posts`() {
        val remotePosts = listOf(mockedPost.copy(1))
        whenever(remoteService.api().getPosts().execute()).thenReturn(
            Response.success(
                remotePosts.map { it.toRemotePost() }
            )
        )

        val testObserver: TestObserver<List<DomainPost>> = remoteDatasource.getPosts().test()
        testObserver.assertValue { it == remotePosts }

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
        whenever(remoteService.api().getPosts().execute())
            .thenReturn(Response.error(400, MockResponseBody()))

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

    // endregion

    // region Post detail

    @Test
    fun `when getRemotePostDetail remote author should be retrieved`() {
        val post = mockedPost.copy(1)

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        verify(remoteService.api()).getAuthor(post.userId)

        testObserver.dispose()
    }

    @Test
    fun `when remote author retrieved should get remote comments`() {
        val post = mockedPost.copy(1)
        val author = mockedAuthor.copy(1)

        whenever(remoteService.api().getAuthor(1).execute()).thenReturn(
            Response.success(
                author.toRemoteAuthor()
            )
        )

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        verify(remoteService.api()).getComments(post.id)

        testObserver.dispose()
    }

    @Test
    fun `when remote comments retrieved post with details should be emitted`() {
        val post = mockedPost.copy(1)
        val author = mockedAuthor.copy(1)
        val comments = listOf(mockedComment.copy(1))
        val postWithDetails = post.copy(author = author, comments = comments)

        whenever(remoteService.api().getAuthor(1).execute()).thenReturn(
            Response.success(
                author.toRemoteAuthor()
            )
        )
        whenever(remoteService.api().getComments(1).execute()).thenReturn(
            Response.success(comments.map { it.toRemoteComment() })
        )

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        testObserver.assertValue { it == postWithDetails }

        testObserver.dispose()
    }

    @Test
    fun `when remote author call fails connection exception should be thrown`() {
        val post = mockedPost.copy(1)

        whenever(remoteService.api().getAuthor(any()).execute()).thenThrow(IOException())

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        testObserver.assertError(ConnectionException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when remote author call return not successful server exception should be thrown`() {
        val post = mockedPost.copy(1)

        whenever(remoteService.api().getAuthor(1).execute()).thenReturn(
            Response.error(400, MockResponseBody())
        )

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        testObserver.assertError(ServerException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when remote author call return no author invalid response should be thrown`() {
        val post = mockedPost.copy(1)

        whenever(remoteService.api().getAuthor(1).execute())
            .thenReturn(Response.success(null))

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        testObserver.assertError(InvalidResponseException::class.java)

        testObserver.dispose()
    }

    @Test
    fun `when remote comments fails return post without comments should be emitted`() {
        val post = mockedPost.copy(1)
        val author = mockedAuthor.copy(1)
        val postWithDetails = post.copy(author = author)

        whenever(remoteService.api().getAuthor(1).execute()).thenReturn(
            Response.success(author.toRemoteAuthor())
        )
        whenever(remoteService.api().getComments(1).execute()).thenReturn(
            Response.error(400, MockResponseBody())
        )

        val testObserver: TestObserver<DomainPost> =
            remoteDatasource.getPostDetail(post).test()
        testObserver.assertValue { it == postWithDetails }

        testObserver.dispose()
    }

    // endregion

    private class MockResponseBody : ResponseBody() {
        override fun contentLength(): Long = 0
        override fun contentType(): MediaType? = null
        override fun source(): BufferedSource = throw NotImplementedError()
    }
}