package com.solvro.topwr.ui.fragments.faq_page

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.FaqFragmentBinding
import com.solvro.topwr.ui.adapters.FaqAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FaqFragment : Fragment() {

    private val viewModel: FaqViewModel by viewModels()
    private lateinit var binding: FaqFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FaqFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        viewModel.infos.observe(viewLifecycleOwner){
            binding.recyclerView.adapter = it.data?.let { infos ->
                FaqAdapter(infos){

                }
            }
        }
    }


}