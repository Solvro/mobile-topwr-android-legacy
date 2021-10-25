package com.solvro.topwr.ui.fragments.departments_details_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.solvro.topwr.databinding.DepartmentsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepartmentsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsDetailsFragment()
    }

    private lateinit var binding: DepartmentsFragmentBinding
    private val viewModel: DepartmentsDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DepartmentsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}