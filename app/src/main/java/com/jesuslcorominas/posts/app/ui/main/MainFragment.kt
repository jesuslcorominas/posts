package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentMainBinding
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.ui.common.BaseFragment
import com.jesuslcorominas.posts.app.ui.common.EventObserver
import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import javax.inject.Inject

class MainFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: PostAdapter

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
    }

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

            recyclerViewPosts.adapter = adapter
        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner, EventObserver { id ->
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(id)
            navController.navigate(action)
        })

        return binding?.root
    }
}