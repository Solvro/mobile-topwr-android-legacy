package com.solvro.topwr.ui.fragments.faq_details

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.solvro.topwr.R
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.FaqDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FaqDetailsFragment : Fragment(R.layout.faq_details_fragment) {

    companion object {
        fun newInstance() = FaqDetailsFragment()
    }

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: FaqDetailsFragmentBinding
    private val viewModel: FaqDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FaqDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val info = viewModel.info.value

        info?.let {
            setTransitionNames(it)
        }

        binding.apply {
            //temporary
            faqDescription.text = info?.description

            backToFaq.setOnClickListener {
                startPostponedEnterTransition()
                findNavController().navigateUp()
            }
        }

        info?.photo?.url?.let { loadImage(it) }
    }

    private fun loadImage(imageUrl: String) {
        glide.load(imageUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

            })
            .into(binding.faqImageView)
    }

    private fun setTransitionNames(info: Info) {
        binding.apply {
            faqImageView.transitionName = getString(R.string.faq_image, info.id)
        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }
}