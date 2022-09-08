package com.solvro.topwr.ui.fragments.about_us_page

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.solvro.topwr.data.model.aboutUs.AboutUs
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.databinding.AboutUsFragmentBinding
import com.solvro.topwr.ui.fragments.faq_details.FaqDetailsFragment
import com.solvro.topwr.utils.MarkdownToText
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.linkify.LinkifyPlugin
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
        val about = viewModel.aboutUs.value

        setupUI(about)
    }

    private fun setupUI(aboutUs: AboutUs?) {
        setTransitionNames()

        binding.apply {
            backToFaq.setOnClickListener {
                startPostponedEnterTransition()
                findNavController().navigateUp()
            }

            aboutUs?.content?.let { content ->
                context?.let { context ->
                    MarkdownToText.createTextFromMarkdown(
                        context = context,
                        description = content,
                        textView = aboutUsContent,
                        codeTextColor = context.getColor(R.color.faq_normal_text_color),
                        linkColor = context.getColor(R.color.faq_link),
                        isLinkUnderlined = true,
                    )
                }
            }

            aboutUs?.photo?.url?.let {
                loadImage(it)
            }
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
            .into(binding.aboutUsImageView)
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