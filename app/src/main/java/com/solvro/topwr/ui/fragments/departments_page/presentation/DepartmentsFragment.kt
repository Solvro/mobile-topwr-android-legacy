package com.solvro.topwr.ui.fragments.departments_page.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.R
import com.solvro.topwr.databinding.DepartmentsFragmentBinding
import com.solvro.topwr.ui.adapters.DefaultLoadStateAdapter
import com.solvro.topwr.ui.fragments.departments_page.DepartmentsViewModel
import com.solvro.topwr.utils.gone
import com.solvro.topwr.utils.visible
import com.solvro.topwr.utils.withLoadStateAdapters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DepartmentsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsFragment()
    }

    private lateinit var binding: DepartmentsFragmentBinding
    private val viewModel: DepartmentsViewModel by viewModels()
    private var departmentsAdapter = DepartmentsAdapter(DepartmentComparator) {
        val action = DepartmentsFragmentDirections.actionDepartmentsFragmentToDepartmentsDetailsFragment(
                getString(R.string.departments)
            )
        action.departmentInfo = it
        findNavController().navigate(action)
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
        binding = DepartmentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupListeners()
        setObservers()
    }

    private fun setupListeners() {
        binding
            .departmentSearchBar
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(text: String?): Boolean = false

                override fun onQueryTextChange(text: String?): Boolean {
                    viewModel.setTextFilter(text ?: "")
                    return false
                }
            })
        binding.departmentListSwipeLayout.setOnRefreshListener {
            departmentsAdapter.refresh()
        }
    }

    private fun setupRecyclerView() {
        binding.departmentRecyclerView.apply {
            adapter = departmentsAdapter.withLoadStateAdapters(
                DefaultLoadStateAdapter(departmentsAdapter::retry),
                DefaultLoadStateAdapter(departmentsAdapter::retry)
            )
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setObservers() {
        with(viewModel) {
            departments.observe(viewLifecycleOwner) {
                binding.departmentListSwipeLayout.isRefreshing = false
                lifecycleScope.launch { departmentsAdapter.submitData(it) }
            }
        }
    }

    private fun setEmptyView(isEmpty: Boolean) {
        binding.apply {
            if (isEmpty) {
                departmentListSwipeLayout.gone()
                departmentListEmptyViev.visible()
            } else {
                departmentListSwipeLayout.visible()
                departmentListEmptyViev.gone()
            }
        }
    }
}