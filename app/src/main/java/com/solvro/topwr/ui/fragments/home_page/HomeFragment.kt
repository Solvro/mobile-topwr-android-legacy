package com.solvro.topwr.ui.fragments.home_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.solvro.topwr.databinding.HomeFragmentBinding
import com.solvro.topwr.ui.adapters.DepartmentsHomeAdapter
import com.solvro.topwr.ui.adapters.BuildingsAdapter
import com.solvro.topwr.ui.adapters.ScienceClubsAdapter
import com.solvro.topwr.ui.adapters.WhatsUpAdapter
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
        binding.buildingsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.departmentsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.whatsUpRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.scienceClubsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewmodelHandler()
    }

    @SuppressLint("SetTextI18n")
    private fun viewmodelHandler() {
        //livedata for end date and bind data
        viewModel.endDate.observe(viewLifecycleOwner) {
            binding.textViewNumber1.text = it[0] + ""
            binding.textViewNumber2.text = it[1] + ""
            binding.textViewNumber3.text = it[2] + ""
        }
        viewModel.departments.observe(viewLifecycleOwner) {
            binding.departmentsRecyclerView.adapter =
                it.data?.let { it1 ->
                    DepartmentsHomeAdapter(it1) { chosenDepartment ->
                        Toast.makeText(context, chosenDepartment.Code, Toast.LENGTH_SHORT).show()
                    }
                }
        }
        viewModel.buildings.observe(viewLifecycleOwner) { buildings ->
            binding.buildingsRecyclerView.adapter =
                buildings.data?.let {
                    BuildingsAdapter(it) { chosenBuilding ->
                        Toast.makeText(context, chosenBuilding.Code, Toast.LENGTH_SHORT).show()
                    }
                }
        }
        viewModel.notices.observe(viewLifecycleOwner) {
            it.message?.let { it1 -> Log.i("testy", it1) }
            Log.i("status", it.status.toString())
            binding.whatsUpRecyclerView.adapter = it.data?.let { notices ->
                WhatsUpAdapter(notices) { chosenItem ->
                    Toast.makeText(context, chosenItem.Title, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.scienceClubs.observe(viewLifecycleOwner){
            binding.scienceClubsRecyclerView.adapter = it.data?.let { scienceCLubs->
                ScienceClubsAdapter(scienceCLubs){ scienceClubItem ->
                    Toast.makeText(context, scienceClubItem.Name, Toast.LENGTH_SHORT).show()

                }
            }

        }
    }

}