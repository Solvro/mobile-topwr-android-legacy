package com.solvro.topwr.ui.fragments.whats_up_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.solvro.topwr.R
import com.solvro.topwr.databinding.WhatsUpFragmentBinding

//@AndroidEntryPoint
class WhatsUpFragment : Fragment(R.layout.whats_up_fragment) {

    companion object{
        fun newInstance() = WhatsUpFragment()
    }

    //@Inject
    //lateinit var glide: RequestManager

    private lateinit var binding: WhatsUpFragmentBinding
    private val viewModel: WhatsUpViewModel by viewModels()
    //private val args: WhatsUpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WhatsUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val notice = args.notice;

        glide.load(notice.Photo?.url).into(binding.whatsUpImageView)

        binding.apply {
            whatsUpTitle.text = notice.Title
            whatsUpNewsDescription.text = notice.Description
            whatsUpDate.text = notice.published_at

            backToMainBtn.setOnClickListener {
                activity?.onBackPressed()
            }
        }
        */
    }

    /*
       //example di for glide

       @Singleton
       @Provides
       fun provideGlideInstance(
           @ApplicationContext context: Context
       ) = Glide.with(context).setDefaultRequestOptions(
           RequestOptions()
               .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
               //.placeholder()
               //.error()
               .priority(Priority.NORMAL)
       )
   */

}