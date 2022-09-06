package com.solvro.topwr.ui.fragments.no_internet_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.solvro.topwr.databinding.NoInternetFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoInternetFragment : Fragment() {

    companion object {
        fun newInstance() = NoInternetFragment()
    }

    private lateinit var binding: NoInternetFragmentBinding
    private val viewModel: NoInternetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoInternetFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }


}