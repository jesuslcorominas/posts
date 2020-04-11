package com.jesuslcorominas.posts.app.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jesuslcorominas.posts.domain.ConnectionException
import com.jesuslcorominas.posts.domain.InvalidResponseException
import com.jesuslcorominas.posts.domain.Post
import com.jesuslcorominas.posts.domain.ServerException
import com.jesuslcorominas.posts.usecases.GetPostUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(private val getPostUseCase: GetPostUseCase) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val _items: MutableLiveData<List<Post>> = MutableLiveData()
    val items: LiveData<List<Post>> get() = _items

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _hasError: MutableLiveData<Boolean> = MutableLiveData()
    val hasError: LiveData<Boolean> get() = _hasError

    private val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> get() = _error

    init {
        getPosts()
    }

    fun getPosts() {
        _loading.value = true
        hideError()

        disposables.add(
            getPostUseCase.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Post>>() {
                    override fun onSuccess(t: List<Post>) {
                        _items.value = t
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
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

    private fun hideError() {
        _hasError.value = false
        _error.value = Exception()
    }

    private fun showError(e: Throwable) {
        _error.value = e
        _hasError.value = true
    }
}
