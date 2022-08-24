package com.solvro.topwr.ui.fragments.departments_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.DepartmentsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DepartmentsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsFragment()
    }

    private lateinit var binding: DepartmentsFragmentBinding
    private val viewModel: DepartmentsViewModel by viewModels()
    private var departmentsAdapter = DepartmentsAdapter(DepartmentComparator)

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
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.departmentRecyclerView.apply {
            adapter = departmentsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        with(viewModel) {
            departments.observe(viewLifecycleOwner) {
                lifecycleScope.launch { departmentsAdapter.submitData(it) }
            }
        }
    }
}