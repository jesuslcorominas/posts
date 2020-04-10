package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.ui.common.BaseActivity
import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
