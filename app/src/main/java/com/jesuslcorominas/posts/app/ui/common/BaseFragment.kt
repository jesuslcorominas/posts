package com.jesuslcorominas.posts.app.ui.common

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    abstract val name: String?

    override fun onResume() {
        super.onResume()

        trackScreen(name)
    }
}