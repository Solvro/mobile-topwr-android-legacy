package com.solvro.topwr.features.scienceclub.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.solvro.topwr.R
import com.solvro.topwr.databinding.ScienceClubsFragmentBinding
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.ui.adapters.DefaultLoadStateAdapter
import com.solvro.topwr.utils.SpaceItemDecoration
import com.solvro.topwr.utils.gone
import com.solvro.topwr.utils.visible
import com.solvro.topwr.utils.withLoadStateAdapters
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
    private var categoriesAdapter: ScienceClubsCategoriesAdapter = ScienceClubsCategoriesAdapter {
        viewModel.setCategoriesFilter(if (it == getString(R.string.all_tag_name)) null else it)
    }
    private var scienceClubsAdapter: ScienceClubsAdapter =
        ScienceClubsAdapter(ScienceClubComparator) { scienceClub, logoView, nameTextView ->
            navigateToDetails(scienceClub, logoView, nameTextView)
        }.apply {
            addLoadStateListener { loadState ->
                // Checks if data is empty
                val isEmpty = loadState.source.refresh is LoadState.NotLoading
                        && loadState.append.endOfPaginationReached
                        && this.itemCount < 1
                setEmptyView(isEmpty)
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
        setupSharedTransition()
    }

    private fun setListeners() {
        binding.apply {
            scienceClubsSearchBar.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = false

                override fun onQueryTextChange(query: String?): Boolean {
                    viewModel.setTextFilter(query ?: "")
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
            selectedCategory.observe(viewLifecycleOwner) {
                categoriesAdapter.selectCategory(it ?: getString(R.string.all_tag_name))
            }
            scienceClubs.observe(viewLifecycleOwner) {
                binding.scienceClubRefreshLayout.isRefreshing = false
                lifecycleScope.launch { scienceClubsAdapter.submitData(it) }
            }
            scienceClubTags.observe(viewLifecycleOwner) { tags ->
                categoriesAdapter.setData(mutableListOf<String>().apply {
                    add(getString(R.string.all_tag_name))
                    addAll(tags)
                })
            }
        }
    }

    private fun setupScienceClubsRecyclerView() {
        binding.scienceClubsRecyclerView.apply {
            adapter = scienceClubsAdapter.withLoadStateAdapters(
                header = DefaultLoadStateAdapter(scienceClubsAdapter::retry),
                footer = DefaultLoadStateAdapter(scienceClubsAdapter::retry)
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

    private fun setEmptyView(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                scienceClubRefreshLayout.gone()
                scienceClubEmptyView.visible()
            } else {
                scienceClubRefreshLayout.visible()
                scienceClubEmptyView.gone()
            }
        }
    }

    private fun navigateToDetails(scienceClub: ScienceClub, logoView: View, nameTextView: View) {
        val action =
            ScienceClubsFragmentDirections.actionScienceClubsFragmentToScienceClubsDetailsFragment(
                scienceClub
            )
        val extras = FragmentNavigator.Extras.Builder().addSharedElements(
            mapOf(
                logoView to "science_club_logo",
                nameTextView to "science_club_name"
            )
        ).build()
        findNavController().navigate(action, navigatorExtras = extras)
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.move)
    }
}