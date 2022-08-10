package com.solvro.topwr.ui.fragments.science_clubs_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.ScienceClubsFragmentBinding
import com.solvro.topwr.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class
ScienceClubsFragment : Fragment() {

    companion object {
        fun newInstance() = ScienceClubsFragment()
    }

    private val viewModel: ScienceClubsViewModel by viewModels()
    private lateinit var binding: ScienceClubsFragmentBinding
    private lateinit var scienceClubsAdapter: ScienceClubsAdapter
    private lateinit var categoriesAdapter: ScienceClubsCategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ScienceClubsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupScienceClubsRecyclerView()
        setupCategoryRecyclerView()
        setObservers()
        binding.scienceClubsSearchBar.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.setTextFilter(query ?: "")
                return false
            }

        })
    }

    private fun setObservers() {
        viewModel.apply{
            scienceClubs.observe(viewLifecycleOwner) {
                scienceClubsAdapter.setData(it.data ?: listOf())
            }
            categoriesState.observe(viewLifecycleOwner) {
                categoriesAdapter.setData(it.allCategories, it.selectedCategories)
            }
        }
    }

    private fun setupScienceClubsRecyclerView() {
        this.scienceClubsAdapter = ScienceClubsAdapter()
        binding.apply {
            scienceClubsRecyclerView.apply {
                adapter = this@ScienceClubsFragment.scienceClubsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        categoriesAdapter = ScienceClubsCategoriesAdapter {
            viewModel.toggleCategory(it)
        }
        binding.scienceClubsCategoriesRecyclerView.apply {
            adapter = categoriesAdapter
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