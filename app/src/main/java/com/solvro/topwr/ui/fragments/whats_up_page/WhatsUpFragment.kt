package com.solvro.topwr.ui.fragments.whats_up_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.solvro.topwr.databinding.WhatsUpFragmentBinding

class WhatsUpFragment : Fragment() {

    companion object{
        fun newInstance() = WhatsUpFragment()
    }

    //inject glide for photo loading?
    private lateinit var binding: WhatsUpFragmentBinding
    private val viewModel: WhatsUpViewModel by viewModels()
    //private val args: whats_up_fragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WhatsUpFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}