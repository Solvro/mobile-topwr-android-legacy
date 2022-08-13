package com.solvro.topwr.ui.fragments.science_clubs_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.ScienceClubsFragmentBinding
import com.solvro.topwr.utils.SpaceItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        setClickListeners()
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
        binding.scienceClubRefreshLayout.setOnRefreshListener {
            scienceClubsAdapter.refresh()
        }
    }

    private fun setClickListeners() {
        binding.scienceClubsFilterBtn.setOnClickListener {
            viewModel.getScienceClubTags()
        }
    }

    private fun setObservers() {
        viewModel.apply {
            selectedCategories.observe(viewLifecycleOwner) {
                categoriesAdapter.setData(it, it)
            }
            scienceClubs.observe(viewLifecycleOwner) {
                binding.scienceClubRefreshLayout.isRefreshing = false
                lifecycleScope.launch { scienceClubsAdapter.submitData(it) }
            }
            scienceClubTags.observe(viewLifecycleOwner) { tags ->
                TagsDialog(tags) {
                    val filters = if (it == null) listOf() else listOf(it)
                    viewModel.setCategoriesFilter(filters)
                }.show(childFragmentManager, "dialog")
            }
        }
    }

    private fun setupScienceClubsRecyclerView() {
        this.scienceClubsAdapter = ScienceClubsAdapter(ScienceClubComparator)
        binding.apply {
            scienceClubsRecyclerView.apply {
                adapter = this@ScienceClubsFragment.scienceClubsAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        categoriesAdapter = ScienceClubsCategoriesAdapter {}
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