package com.solvro.topwr.ui.fragments.whats_up_page

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.solvro.topwr.R
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.databinding.WhatsUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WhatsUpFragment : Fragment(R.layout.whats_up_fragment) {

    companion object {
        fun newInstance() = WhatsUpFragment()
    }

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: WhatsUpFragmentBinding
    private val viewModel: WhatsUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WhatsUpFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notice = viewModel.notice.value

        (view.parent as? ViewGroup)?.doOnPreDraw {
            startPostponedEnterTransition()
        }

        notice?.let {
            setTransitionNames(it)
        }

        binding.apply {
            whatsUpTitle.text = notice?.title
            whatsUpNewsDescription.text = notice?.description
            whatsUpDate.text = notice?.getFormattedTime()

            backToMainBtn.setOnClickListener {
                startPostponedEnterTransition()
                findNavController().navigateUp()
            }
        }

        notice?.photo?.url?.let { loadImage(it) }
    }

    private fun setTransitionNames(notice: Notices){
        binding.apply {
            whatsUpImageView.transitionName = getString(R.string.whatsup_image, notice.id)
            whatsUpTitle.transitionName = getString(R.string.whatsup_title, notice.id)
            whatsUpDate.transitionName = getString(R.string.whatsup_date, notice.id)
            whatsUpNewsDescription.transitionName = getString(R.string.whatsup_description, notice.id)
        }
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
            .into(binding.whatsUpImageView)
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }
}