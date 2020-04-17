package com.jesuslcorominas.posts.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.posts.app.analytics.ClickPostEvent
import com.jesuslcorominas.posts.app.analytics.GetPostsEvent
import com.jesuslcorominas.posts.app.ui.common.BaseViewModel
import com.jesuslcorominas.posts.app.ui.common.Event
import com.jesuslcorominas.posts.domain.*
import com.jesuslcorominas.posts.domain.analytics.AnalyticsTracker
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(
    private val getPostUseCase: GetPostUseCase,
    private val analyticsTracker: AnalyticsTracker
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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Post>>() {
                    override fun onSuccess(items: List<Post>) {
                        _items.value = items
                        _loading.value = false
                    }

                    override fun onError(e: Throwable) {
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
            Timber.i("Post \"$title\" seleccionado con id $id")
            analyticsTracker.track(ClickPostEvent(post.id))

            _navigateToDetail.value = Event(id)
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }
}
