package com.jesuslcorominas.posts.app.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.posts.app.ui.TrampolineSchedulerProvider
import com.jesuslcorominas.posts.app.ui.common.Event
import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.testshared.mockedPost
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getPostUseCase: GetPostUseCase = mock()
    private val analyticsTracker: AnalyticsTracker = mock()
    private val schedulerProvider: SchedulerProvider = TrampolineSchedulerProvider()

    private lateinit var mainViewModel: MainViewModel

    @Test
    fun `init viewmodel should call getPosts use case`() {
        val posts = listOf(mockedPost.copy(1))

        whenever(getPostUseCase.getPosts()).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                posts
            )
        })

        mainViewModel = MainViewModel(getPostUseCase, analyticsTracker, schedulerProvider)

        verify(getPostUseCase).getPosts()
    }

    @Test
    fun `when getPosts fail hasError should be true`() {
        whenever(getPostUseCase.getPosts()).thenReturn(Single.create {
            it.onError(
                ConnectionException()
            )
        })

        mainViewModel = MainViewModel(getPostUseCase, analyticsTracker, schedulerProvider)

        assertTrue(mainViewModel.hasError.value!!)
    }

    @Test
    fun `when getPosts success posts should be retrieved`() {
        val posts = listOf(mockedPost.copy(1))

        whenever(getPostUseCase.getPosts()).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                posts
            )
        })

        mainViewModel = MainViewModel(getPostUseCase, analyticsTracker, schedulerProvider)
        assertEquals(mainViewModel.items.value!!, posts)
    }

    @Test
    fun `onPostClick should launch event navigate to detail`() {
        val observerNavigateToDetailEvent: Observer<Event<Int>> = mock()

        val post = mockedPost.copy(1)
        val posts = listOf(post.copy(1))

        whenever(getPostUseCase.getPosts()).thenReturn(Single.create { emitter ->
            emitter.onSuccess(
                posts
            )
        })

        mainViewModel = MainViewModel(getPostUseCase, analyticsTracker, schedulerProvider)

        mainViewModel.navigateToDetail.observeForever(observerNavigateToDetailEvent)

        mainViewModel.onPostClicked(post)

        verify(observerNavigateToDetailEvent).onChanged(mainViewModel.navigateToDetail.value)
    }
}