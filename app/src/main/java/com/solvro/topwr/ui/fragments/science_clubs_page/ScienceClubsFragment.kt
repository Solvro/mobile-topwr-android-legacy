package com.solvro.topwr.ui.fragments.science_clubs_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.ScienceClubsFragmentBinding
import com.solvro.topwr.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class
ScienceClubsFragment : Fragment() {

    companion object {
        fun newInstance() = ScienceClubsFragment()
    }

    private val viewModel: ScienceClubsViewModel by viewModels()
    private lateinit var binding: ScienceClubsFragmentBinding
    private var categoriesAdapter: ScienceClubsCategoriesAdapter = ScienceClubsCategoriesAdapter {}
    private var onQueryChangeJob: Job? = null
    private var scienceClubsAdapter: ScienceClubsAdapter =
        ScienceClubsAdapter(ScienceClubComparator).apply {
            addLoadStateListener { loadState ->
                if (
                    loadState.source.refresh is LoadState.NotLoading
                    && loadState.append.endOfPaginationReached
                    && this.itemCount < 1
                ) {
                    binding.apply {
                        scienceClubRefreshLayout.gone()
                        scienceClubEmptyView.visible()
                    }
                } else {
                    binding.apply {
                        scienceClubRefreshLayout.visible()
                        scienceClubEmptyView.gone()
                    }
                }
            }
        }

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
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            scienceClubsFilterBtn.setOnClickListener {
                viewModel.getScienceClubTags()
            }
            scienceClubsSearchBar.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    onQueryChangeJob?.cancel()
                    onQueryChangeJob = lifecycleScope.launch {
                        delay(Constants.DEFAULT_DEBOUNCE_TIME_MS)
                        viewModel.setTextFilter(query ?: "")
                    }
                    return false
                }
            })
            scienceClubRefreshLayout.setOnRefreshListener {
                scienceClubsAdapter.refresh()
            }
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
                    viewModel.setCategoriesFilter(it)
                }.show(childFragmentManager, "dialog")
            }
            areTagsAvailable.observe(viewLifecycleOwner) {
                binding.scienceClubsFilterBtn.isVisible = it
                binding.scienceClubsFilterBtn.isEnabled = it
            }
        }
    }

    private fun setupScienceClubsRecyclerView() {
        binding.scienceClubsRecyclerView.apply {
            adapter = scienceClubsAdapter.withLoadStateAdapters(
                header = ScienceClubsLoadStateAdapter(scienceClubsAdapter::retry),
                footer = ScienceClubsLoadStateAdapter(scienceClubsAdapter::retry)
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupCategoryRecyclerView() {
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