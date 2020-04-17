package com.jesuslcorominas.posts.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jesuslcorominas.posts.app.ui.common.BaseViewModel
import com.jesuslcorominas.posts.domain.*
import com.jesuslcorominas.posts.usecases.GetPostDetailUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DetailViewModel(
    private val postId: Int, private val getPostDetailUseCase: GetPostDetailUseCase
) : BaseViewModel() {

    private val disposables = CompositeDisposable()

    private val _post: MutableLiveData<Post> = MutableLiveData()
    val post: LiveData<Post> get() = _post

    init {
        getPostDetail()
    }

    fun getPostDetail() {
        _loading.value = true
        hideError()

        disposables.add(
            getPostDetailUseCase.getPostDetailUseCase(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Post>() {
                    override fun onSuccess(post: Post) {
                        _post.value = post
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
                            is DatabaseException -> Timber.e(
                                e,
                                "Error de base de datos"
                            )
                            else -> Timber.e(e, "Error desconocido")
                        }

                        showError(e)
                        _loading.value = false
                    }
                })
        )
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}
