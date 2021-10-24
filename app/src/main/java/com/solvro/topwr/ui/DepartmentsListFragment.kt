package com.solvro.topwr.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.solvro.topwr.databinding.FragmentDepartmentsListBinding

class DepartmentsListFragment : Fragment() {

    private lateinit var binding: FragmentDepartmentsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDepartmentsListBinding.inflate(inflater, container, false)
        return binding.root
    }
}