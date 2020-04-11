package com.jesuslcorominas.posts.app.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jesuslcorominas.posts.app.App
import com.jesuslcorominas.posts.app.di.ApplicationComponent

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return onCreateView(
            (activity?.application as App).appComponent,
            inflater,
            container,
            savedInstanceState = null
        )
    }

    abstract fun onCreateView(
        applicationComponent: ApplicationComponent,
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
}