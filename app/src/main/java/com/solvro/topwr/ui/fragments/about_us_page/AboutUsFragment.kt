package com.solvro.topwr.ui.fragments.about_us_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.RequestManager
import com.solvro.topwr.R
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.AboutUsFragmentBinding
import com.solvro.topwr.ui.fragments.faq_details.FaqDetailsFragment
import javax.inject.Inject

class AboutUsFragment : Fragment(R.layout.about_us_fragment) {

    companion object {
        fun newInstance() = FaqDetailsFragment()
    }

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: AboutUsFragmentBinding
    private val viewModel: AboutUsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutUsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        setTransitionNames()

        binding.apply {
            backToFaq.setOnClickListener {
                startPostponedEnterTransition()
                findNavController().navigateUp()
            }
      }
    }

    private fun setTransitionNames() {
        binding.apply {
            aboutUsImageView.transitionName = getString(R.string.about_us_image)
        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }
}