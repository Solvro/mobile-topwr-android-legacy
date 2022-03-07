package com.solvro.topwr.ui.fragments.whats_up_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.solvro.topwr.R
import com.solvro.topwr.databinding.WhatsUpFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WhatsUpFragment : Fragment(R.layout.whats_up_fragment) {

    companion object{
        fun newInstance() = WhatsUpFragment()
    }

    @Inject
    lateinit var glide: RequestManager

    private lateinit var binding: WhatsUpFragmentBinding
    private val viewModel: WhatsUpViewModel by viewModels()
    private val args: WhatsUpFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WhatsUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notice = args.notice;

        glide.load(notice.photo?.url).into(binding.whatsUpImageView)

        binding.apply {
            whatsUpTitle.text = notice.title
            whatsUpNewsDescription.text = notice.description

            //tak na szybko bo demo
            val replaced = notice.updated_at?.replace("T", "-")?.split("-")

            val date = "${replaced?.get(2)}.${replaced?.get(1)}.${replaced?.get(0)}"

            whatsUpDate.text = date


            backToMainBtn.setOnClickListener {
                activity?.onBackPressed()
            }
        }

    }


}