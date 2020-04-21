package com.jesuslcorominas.posts.app.ui.common

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.jesuslcorominas.posts.app.App
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.di.ApplicationComponent

fun Fragment.showLicenses() {
    OssLicensesMenuActivity.setActivityTitle(
        getString(R.string.open_source_licenses)
    )
    startActivity<OssLicensesMenuActivity> { }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProvider(this, vmFactory).get()
}

fun BaseFragment.trackScreen(name: String?) {
    activity?.trackScreen(name)
}

fun Fragment.applicationComponent(): ApplicationComponent =
    (activity?.application as App).appComponent

inline fun <reified T : Activity> Fragment.startActivity(body: Intent.() -> Unit) {
    activity?.startActivity<T>(body)
}
