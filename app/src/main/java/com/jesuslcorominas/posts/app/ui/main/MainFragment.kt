package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentMainBinding
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.ui.common.BaseFragment
import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: PostAdapter

    override fun onCreateView(
        applicationComponent: ApplicationComponent,
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applicationComponent.inject(this)

        val binding: FragmentMainBinding? =
            container?.bindingInflate(R.layout.fragment_main, false)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        adapter = PostAdapter(viewModel::onPostClicked)

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MainFragment

            recyclerViewItems.adapter = adapter
        }

        return binding?.root
    }
}