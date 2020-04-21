package com.jesuslcorominas.posts.app.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val mutableLoading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = mutableLoading

    protected val mutableHasError: MutableLiveData<Boolean> = MutableLiveData()
    val hasError: LiveData<Boolean> get() = mutableHasError

    protected val mutableError: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> get() = mutableError

    protected fun hideError() {
        mutableHasError.value = false
        mutableError.value = Exception()
    }

    protected fun showError(e: Throwable) {
        mutableError.value = e
        mutableHasError.value = true
    }
}
