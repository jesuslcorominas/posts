package com.jesuslcorominas.posts.app.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.jesuslcorominas.posts.app.R
import com.jesuslcorominas.posts.app.databinding.FragmentDetailBinding
import com.jesuslcorominas.posts.app.di.ApplicationComponent
import com.jesuslcorominas.posts.app.ui.common.BaseFragment
import com.jesuslcorominas.posts.app.ui.common.ViewModelFactory
import com.jesuslcorominas.posts.app.ui.common.bindingInflate
import javax.inject.Inject

class DetailFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        applicationComponent: ApplicationComponent,
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        applicationComponent.inject(this)

        val binding: FragmentDetailBinding? =
            container?.bindingInflate(R.layout.fragment_detail, false)

        viewModelFactory.postId = args.id
        val viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(DetailViewModel::class.java)

        binding?.apply {
            viewmodel = viewModel
            lifecycleOwner = this@DetailFragment
        }

        return binding?.root
    }
}
