package com.solvro.topwr.ui.fragments.science_clubs_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.ScienceClubsFragmentBinding
import com.solvro.topwr.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScienceClubsFragment : Fragment() {

    companion object {
        fun newInstance() = ScienceClubsFragment()
    }

    private val viewModel: ScienceClubsViewModel by viewModels()
    private lateinit var binding: ScienceClubsFragmentBinding
    private lateinit var adapter: ScienceClubsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScienceClubsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupCategoryRecyclerView()
    }

    private fun setupRecyclerView() {
        this.adapter = ScienceClubsAdapter()
        binding.apply {
            scienceClubsRecyclerView.apply {
                adapter = this@ScienceClubsFragment.adapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        binding.scienceClubsCategoriesRecyclerView.apply {
            adapter = ScienceClubsCategoriesAdapter()
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
            addItemDecoration(
                SpaceItemDecoration(
                    spaceWidth = 16
                )
            )
        }
    }

}