package com.solvro.topwr.ui.fragments.departments_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.DepartmentsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepartmentsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsFragment()
    }

    private lateinit var binding: DepartmentsFragmentBinding
    private val viewModel: DepartmentsViewModel by viewModels()
    private lateinit var adapter: DepartmentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DepartmentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.endDate.observe(viewLifecycleOwner){endDate->
            endDate.message?.let { Log.i("tagiii", it) }
            endDate.data?.EndDate?.let { Log.i("tagiii1", it) }
        }
    }

    private fun setupRecyclerView() {
        adapter = DepartmentsAdapter()
        binding.departmentRecyclerView.adapter = adapter
        binding.departmentRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }


}