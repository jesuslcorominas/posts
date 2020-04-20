package com.jesuslcorominas.posts.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.posts.app.data.analytics.ClickPostEvent
import com.jesuslcorominas.posts.app.data.analytics.GetPostsErrorEvent
import com.jesuslcorominas.posts.app.data.analytics.GetPostsEvent
import com.jesuslcorominas.posts.app.ui.common.BaseViewModel
import com.jesuslcorominas.posts.app.ui.common.Event
import com.jesuslcorominas.posts.app.ui.common.SchedulerProvider
import com.jesuslcorominas.posts.data.source.AnalyticsTracker
import com.jesuslcorominas.posts.domain.*
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import timber.log.Timber

class MainViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val analyticsTracker: AnalyticsTracker,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private val _items: MutableLiveData<List<Post>> = MutableLiveData()
    val items: LiveData<List<Post>> get() = _items

    private val _navigateToDetail: MutableLiveData<Event<Int>> = MutableLiveData()
    val navigateToDetail: LiveData<Event<Int>> get() = _navigateToDetail

    init {
        getPosts()
    }

    fun getPosts() {
        analyticsTracker.track(GetPostsEvent())

        _loading.value = true
        hideError()

        disposables.add(
            getPostUseCase.getPosts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribeWith(object : DisposableSingleObserver<List<Post>>() {
                    override fun onSuccess(items: List<Post>) {
                        _items.value = items
                        _loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        analyticsTracker.track(GetPostsErrorEvent(e::class.simpleName))

                        when (e) {
                            is ConnectionException -> Timber.e(e, "Error de conexion")
                            is ServerException -> Timber.e(
                                e, "Error del servidor: ${e.code} : ${e.message}"
                            )
                            is InvalidResponseException -> Timber.e(
                                e,
                                "Respuesta del servidor no valida"
                            )
                            is DatabaseException -> Timber.e(e, "Error de base de datos")
                            else -> Timber.e(e, "Error desconocido")
                        }

                        showError(e)
                        _loading.value = false
                    }
                })
        )
    }

    fun onPostClicked(post: Post) {
        with(post) {
            analyticsTracker.track(ClickPostEvent(post.id))
            _navigateToDetail.value = Event(id)
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}
