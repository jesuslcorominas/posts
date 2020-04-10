package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.ActivityMainBinding
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.ui.common.BaseActivity
import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: PostAdapter

    override fun onCreate(appComponent: ApplicationComponent, savedInstanceState: Bundle?) {
        appComponent.inject(this)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        adapter = PostAdapter(viewModel::onPostClicked)
        binding.recyclerViewItems.adapter = adapter
    }
}
