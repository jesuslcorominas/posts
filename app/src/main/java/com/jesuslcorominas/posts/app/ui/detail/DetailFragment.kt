package com.jesuslcorominas.posts.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentDetailBinding
import com.jesuslcorominas.posts.app.ui.common.BaseFragment
import com.jesuslcorominas.posts.app.ui.common.applicationComponent
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import com.jesuslcorominas.posts.app.ui.common.getViewModel

class DetailFragment : BaseFragment() {

    override val name: String? = DetailFragment::class.simpleName

    private lateinit var adapter: CommentAdapter

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var component: DetailComponent
    private val viewModel by lazy { getViewModel { component.detaiViewModel } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component = applicationComponent().plus(DetailModule(args.id))

        val binding: FragmentDetailBinding? =
            container?.bindingInflate(R.layout.fragment_detail, false)

        adapter = CommentAdapter()

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment

            recyclerViewComments.adapter = adapter
        }

        return binding?.root
    }
}

