package com.jesuslcorominas.posts.app.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jesuslcorominas.posts.app.ui.TrampolineSchedulerProvider
import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.testshared.mockedAuthor
import com.jesuslcorominas.posts.testshared.mockedComment
import com.jesuslcorominas.posts.testshared.mockedPost
import com.jesuslcorominas.posts.usecases.GetPostDetailUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val postId = 1
    private val analyticsTracker: AnalyticsTracker = mock()
    private val schedulerProvider: SchedulerProvider = TrampolineSchedulerProvider()
    private val getPostDetailUseCase: GetPostDetailUseCase = mock()

    private lateinit var detailViewModel: DetailViewModel

    @Test
    fun `init viewmodel should call get post detail use case`() {
        val post = mockedPost.copy(1)

        whenever(getPostDetailUseCase.getPostDetail(postId)).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                post
            )
        })

        detailViewModel =
            DetailViewModel(postId, getPostDetailUseCase, analyticsTracker, schedulerProvider)

        verify(getPostDetailUseCase).getPostDetail(postId)
    }

    @Test
    fun `when get post detail fail hasError should be true`() {
        whenever(getPostDetailUseCase.getPostDetail(postId)).thenReturn(Single.create {
            it.onError(
                ConnectionException()
            )
        })

        detailViewModel =
            DetailViewModel(postId, getPostDetailUseCase, analyticsTracker, schedulerProvider)

        Assert.assertTrue(detailViewModel.hasError.value!!)
    }

    @Test
    fun `when get post detail success post should be retrieved`() {
        val post = mockedPost.copy(1)
        val author = mockedAuthor.copy(1)
        val comments = listOf(mockedComment.copy(1))
        val postWithDetails = post.copy(author = author, comments = comments)

        whenever(getPostDetailUseCase.getPostDetail(postId)).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                postWithDetails
            )
        })

        detailViewModel =
            DetailViewModel(postId, getPostDetailUseCase, analyticsTracker, schedulerProvider)
        assertEquals(detailViewModel.post.value!!, postWithDetails)
    }

}