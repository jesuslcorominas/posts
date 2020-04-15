package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentMainBinding
import com.jesuslcorominas.posts.app.ui.common.EventObserver
import com.jesuslcorominas.posts.app.ui.common.applicationComponent
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import com.jesuslcorominas.posts.app.ui.common.getViewModel

class MainFragment : Fragment() {

    private lateinit var adapter: PostAdapter

    private lateinit var navController: NavController

    private lateinit var component: MainComponent
    private val viewModel: MainViewModel by lazy { getViewModel { component.mainViewModel } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component = applicationComponent().plus(MainModule())

        val binding: FragmentMainBinding? =
            container?.bindingInflate(R.layout.fragment_main, false)

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