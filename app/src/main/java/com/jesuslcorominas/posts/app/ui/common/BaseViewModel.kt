package com.jesuslcorominas.posts.app.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    protected val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    protected val _hasError: MutableLiveData<Boolean> = MutableLiveData()
    val hasError: LiveData<Boolean> get() = _hasError

    protected val _error: MutableLiveData<Throwable> = MutableLiveData()
    val error: LiveData<Throwable> get() = _error

    protected fun hideError() {
        _hasError.value = false
        _error.value = Exception()
    }

    protected fun showError(e: Throwable) {
        _error.value = e
        _hasError.value = true
    }
}