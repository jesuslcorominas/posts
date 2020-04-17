package com.jesuslcorominas.posts.app.ui.main

import android.os.Bundle
import android.view.*
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentMainBinding
import com.jesuslcorominas.posts.app.ui.common.*


class MainFragment : BaseFragment() {

    override val name: String? = MainFragment::class.simpleName

    private lateinit var adapter: PostAdapter

    private lateinit var navController: NavController

    private lateinit var component: MainComponent
    private val viewModel: MainViewModel by lazy { getViewModel { component.mainViewModel } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_copyright) {
            showLicenses()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

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