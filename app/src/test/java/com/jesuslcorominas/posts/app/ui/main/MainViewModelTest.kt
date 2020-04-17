package com.jesuslcorominas.posts.app.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jesuslcorominas.posts.app.ui.TrampolineSchedulerProvider
import com.jesuslcorominas.posts.app.ui.common.Event
import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val getPostUseCase: GetPostUseCase = mock()
    private val analyticsTracker: AnalyticsTracker = mock()
    private val schedulerProvider: SchedulerProvider = TrampolineSchedulerProvider()

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(getPostUseCase, analyticsTracker, schedulerProvider)
    }

    @Test
    fun `posts list should be null when viewmodel is initialized`() {
        assertNull(mainViewModel.items.value)
    }

    @Test
    fun `init viewmodel should call getPosts use case`() {
        verify(getPostUseCase).getPosts()
    }

    @Test
    fun `while getPost is executing loading should be true`() {
        assertTrue(mainViewModel.loading.value!!)
    }

    @Test
    fun `when getPosts fail hasError should be true`() {
        whenever(getPostUseCase.getPosts()).thenReturn(Single.create {
            it.onError(
                ConnectionException()
            )
        })

        getPostUseCase.getPosts()

        assertNotNull(mainViewModel.hasError.value)
    }

    @Test
    fun `onPostClick should launch event navigate to detail`() {
        val observerNavigateToDetailEvent: Observer<Event<Int>> = mock()

        mainViewModel.navigateToDetail.observeForever(observerNavigateToDetailEvent)

        mainViewModel.onPostClicked(any())

        verify(observerNavigateToDetailEvent).onChanged(mainViewModel.navigateToDetail.value)
    }
}