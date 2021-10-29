package com.solvro.topwr.ui.fragments.home_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.R
import com.solvro.topwr.data.model.department.DepartmentItem
import com.solvro.topwr.databinding.HomeFragmentBinding
import com.solvro.topwr.ui.adapters.RecentlySearchedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recentlySearchRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        var list: List<DepartmentItem> = listOf(
            DepartmentItem("0", "Wydział Architektury", "W-1", ""),
            DepartmentItem("1", "Wydział Budownictwa", "W-2", ""),
            DepartmentItem("2","Wydział lfgd", "W-3",""),
            DepartmentItem("3", "Wydział Informatyki", "W-4","")
        )
        binding.recentlySearchRecyclerView.adapter = RecentlySearchedAdapter(list){chosenDepartment ->
            Toast.makeText(context,chosenDepartment.code,Toast.LENGTH_SHORT).show()
        }
    }


}