package com.solvro.topwr.ui.fragments.faq_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.FaqFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FaqFragment : Fragment() {

    private val viewModel: FaqViewModel by viewModels()
    private val adapter = FaqAdapter {
        // do on info click
    }
    private lateinit var binding: FaqFragmentBinding
    private var searchViewJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FaqFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setObservers()
    }

    private fun setObservers() {
        viewModel.infos.observe(viewLifecycleOwner) {
            it.data?.let { infosData ->
                adapter.setData(infosData)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext())
            adapter = this@FaqFragment.adapter
        }
    }

    private fun setupSearchView() {
        binding.faqSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean = false

            override fun onQueryTextChange(text: String?): Boolean {
                searchViewJob?.cancel()
                searchViewJob = lifecycleScope.launch {
                    delay(500)
                    viewModel.setTextFilter(text ?: "")
                }
                return false
            }

        })
    }


}