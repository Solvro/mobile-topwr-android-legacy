package com.solvro.topwr.ui.fragments.home_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.solvro.topwr.R
import com.solvro.topwr.databinding.HomeFragmentBinding
import com.solvro.topwr.ui.adapters.BuildingsAdapter
import com.solvro.topwr.ui.adapters.DepartmentsHomeAdapter
import com.solvro.topwr.ui.adapters.ScienceClubsAdapter
import com.solvro.topwr.ui.adapters.WhatsUpAdapter
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import java.util.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedTransition()
    }

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
        binding.scienceClubsHomeRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        viewmodelHandler()
    }


    @SuppressLint("SetTextI18n")
    private fun viewmodelHandler() {

        //livedata for end date and bind data
        viewModel.endDate.observe(viewLifecycleOwner) {
            if (it.length == 3) {
                binding.textViewNumber1.text = it[0] + ""
                binding.textViewNumber2.text = it[1] + ""
                binding.textViewNumber3.text = it[2] + ""
            } else {
                binding.textViewNumber1.text = "0"
                binding.textViewNumber2.text = "0"
                binding.textViewNumber3.text = "0"
            }

        }
        viewModel.departments.observe(viewLifecycleOwner) {
            if (it.status == Resource.Status.LOADING) {
                binding.departmentsRecyclerView.loadSkeleton(R.layout.skeleton_departments_home_item)
                return@observe
            }
            binding.departmentsRecyclerView.apply {
                hideSkeleton()
                adapter =
                    it.data?.let { it1 ->
                        DepartmentsHomeAdapter(it1) { chosenDepartment ->
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToDepartmentsDetailsFragment(
                                    HomeFragment::class.java.name
                                )
                            action.departmentInfo = chosenDepartment
                            findNavController().navigate(action)
                        }
                    }
            }
        }
        viewModel.buildings.observe(viewLifecycleOwner) { buildings ->
            if (buildings.status == Resource.Status.LOADING) {
                binding.buildingsRecyclerView.loadSkeleton(R.layout.skeleton_buildings_item)
                return@observe
            }
            binding.buildingsRecyclerView.apply {
                hideSkeleton()
                adapter =
                    buildings.data?.let {
                        BuildingsAdapter(it) { chosenBuilding ->
                            Toast.makeText(context, chosenBuilding.code, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        viewModel.notices.observe(viewLifecycleOwner) {
            if (it.status == Resource.Status.LOADING) {
                binding.whatsUpRecyclerView.loadSkeleton(R.layout.skeleton_whats_up_item)
                return@observe
            }

            binding.whatsUpRecyclerView.apply {
                hideSkeleton()
                adapter = it.data?.let { notices ->
                    WhatsUpAdapter(notices) { notice, imageView, title, date, dsc ->
                        val extras = FragmentNavigator.Extras.Builder().addSharedElements(
                            mapOf(
                                imageView to getString(R.string.whatsup_image, notice.id),
                                title to getString(R.string.whatsup_title, notice.id),
                                date to getString(R.string.whatsup_date, notice.id),
                                dsc to getString(R.string.whatsup_description, notice.id)
                            )
                        ).build()

                        val action =
                            HomeFragmentDirections.actionHomeFragmentToWhatsUpFragment(notice)
                        findNavController().navigate(action, extras)
                    }
                }
            }

            (view!!.parent as? ViewGroup)?.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
        viewModel.scienceClubs.observe(viewLifecycleOwner) {
            if (it.status == Resource.Status.LOADING) {
                binding.scienceClubsHomeRecyclerView.loadSkeleton(R.layout.skeleton_science_clubs_item)
                return@observe
            }
            binding.scienceClubsHomeRecyclerView.apply {
                hideSkeleton()
                adapter =
                    ScienceClubsAdapter(it.data ?: listOf()) { scienceClubItem ->
                        Toast.makeText(context, scienceClubItem.name, Toast.LENGTH_SHORT).show()
                    }
            }
        }
        viewModel.dateWeek.observe(viewLifecycleOwner)
        { date ->
            when (date.day) {
                Calendar.SUNDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even_f) + " " + getString(R.string.Sunday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd_f) + " " + getString(R.string.Sunday)
                }
                Calendar.MONDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even) + " " + getString(R.string.Monday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd) + " " + getString(R.string.Monday)
                }
                Calendar.TUESDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even) + " " + getString(R.string.Tuesday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd) + " " + getString(R.string.Tuesday)
                }
                Calendar.WEDNESDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even_f) + " " + getString(R.string.Wednesday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd_f) + " " + getString(R.string.Wednesday)
                }
                Calendar.THURSDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even) + " " + getString(R.string.Thursday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd) + " " + getString(R.string.Thursday)
                }
                Calendar.FRIDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even) + " " + getString(R.string.Friday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd) + " " + getString(R.string.Friday)
                }
                Calendar.SATURDAY -> {
                    if (date.even) binding.textViewDay.text =
                        getString(R.string.Even_f) + " " + getString(R.string.Saturday)
                    else binding.textViewDay.text =
                        getString(R.string.Odd_f) + " " + getString(R.string.Saturday)
                }
            }

        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }
}