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

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error;

    init {
        getPosts()
    }

    private fun getPosts() {
        disposables.add(
            getPostUseCase.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Post>>() {
                    override fun onSuccess(t: List<Post>) {
                        _items.value = t
                    }

                    override fun onError(e: Throwable) {
                        when (e) {
                            is ConnectionException -> _error.value = "Error de conexion"
                            is ServerException -> _error.value = "Error del servidor"
                            is InvalidResponseException -> _error.value =
                                "No se han obtenido resultados"
                            else -> _error.value = "Error desconocido"
                        }
                    }
                })
        )
    }

    fun onPostClicked(post: Post) {
        with(post) {
            Timber.i("Post \"${title}\" seleccionado con id ${id}")
        }
    }

    override fun onCleared() {
        super.onCleared()

        disposables.clear()
    }

}
