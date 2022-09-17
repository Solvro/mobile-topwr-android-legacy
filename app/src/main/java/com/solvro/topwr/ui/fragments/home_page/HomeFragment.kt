package com.solvro.topwr.ui.fragments.home_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.solvro.topwr.R
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.databinding.HomeFragmentBinding
import com.solvro.topwr.ui.MainActivity
import com.solvro.topwr.utils.AcademicDayMapper
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by viewModels()

    /* Adapters */
    private val buildingsAdapter = BuildingsAdapter { chosenBuilding ->
        navigateToBuilding(chosenBuilding)
    }

    private val scienceClubAdapter = ScienceClubsAdapter { scienceClubItem ->
        navigateToScienceClubDetails(scienceClubItem)
    }

    private val departmentsHomeAdapter = DepartmentsHomeAdapter { chosenDepartment ->
        val action =
            HomeFragmentDirections.actionHomeFragmentToDepartmentsDetailsFragment(
                HomeFragment::class.java.name
            )
        action.departmentInfo = chosenDepartment
        findNavController().navigate(action)
    }

    private val whatsUpAdapter =
        WhatsUpAdapter { notice: Notices, noticeIV: ImageView, titleTV: TextView,
                         dateTV: TextView, descTV: TextView ->
            val extras = FragmentNavigator.Extras.Builder().addSharedElements(
                mapOf(
                    noticeIV to getString(R.string.whatsup_image, notice.id),
                    titleTV to getString(R.string.whatsup_title, notice.id),
                    dateTV to getString(R.string.whatsup_date, notice.id),
                    descTV to getString(R.string.whatsup_description, notice.id)
                )
            ).build()

            val action =
                HomeFragmentDirections.actionHomeFragmentToWhatsUpFragment(
                    notice
                )
            findNavController().navigate(action, extras)
        }

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
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerViews()
        setObservers()
        setListeners()
    }

    private fun setupRecyclerViews() {
        fun getDefaultLayoutManager() =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        binding.apply {
            buildingsRecyclerView.apply {
                layoutManager = getDefaultLayoutManager()
                adapter = buildingsAdapter
            }
            departmentsRecyclerView.apply {
                layoutManager = getDefaultLayoutManager()
                adapter = departmentsHomeAdapter
            }
            whatsUpRecyclerView.apply {
                layoutManager = getDefaultLayoutManager()
                adapter = whatsUpAdapter
            }
            scienceClubsHomeRecyclerView.apply {
                layoutManager = getDefaultLayoutManager()
                adapter = scienceClubAdapter
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun setObservers() {
        viewModel.apply {
            endDate.observe(viewLifecycleOwner) {
                binding.textViewNumber1.text = (it.getOrNull(0) ?: "0").toString()
                binding.textViewNumber2.text = (it.getOrNull(1) ?: "0").toString()
                binding.textViewNumber3.text = (it.getOrNull(2) ?: "0").toString()
            }

            dateWeek.observe(viewLifecycleOwner) { date ->
                binding.textViewDay.text =
                    AcademicDayMapper.mapAcademicScheduleDay(requireContext(), date)
            }

            departments.observe(viewLifecycleOwner) {
                binding.departmentsRecyclerView.apply {
                    if (it.status == Resource.Status.LOADING) {
                        loadSkeleton(R.layout.skeleton_departments_home_item)
                    } else {
                        hideSkeleton()
                        it.data?.let { data ->
                            departmentsHomeAdapter.setData(data)
                        }
                    }
                }
            }

            buildings.observe(viewLifecycleOwner) { buildings ->
                binding.buildingsRecyclerView.apply {
                    if (buildings.status == Resource.Status.LOADING) {
                        loadSkeleton(R.layout.skeleton_buildings_item)
                    } else {
                        hideSkeleton()
                        buildings.data?.let { data -> buildingsAdapter.setData(data) }
                    }
                }
            }

            notices.observe(viewLifecycleOwner) {
                binding.whatsUpRecyclerView.apply {
                    if (it.status == Resource.Status.LOADING) {
                        loadSkeleton(R.layout.skeleton_whats_up_item)
                    } else {
                        hideSkeleton()
                        it.data?.let { notices -> whatsUpAdapter.setData(notices) }
                    }
                }
            }

            scienceClubs.observe(viewLifecycleOwner) {
                binding.scienceClubsHomeRecyclerView.apply {
                    if (it.status == Resource.Status.LOADING) {
                        loadSkeleton(R.layout.skeleton_science_clubs_item)
                    } else {
                        hideSkeleton()
                        it.data?.let { data -> scienceClubAdapter.setData(data) }
                    }
                }
            }
        }
    }

    private fun setListeners() {
        fun changePage(page: MainActivity.BottomNavPage) {
            val mainActivity = (requireActivity() as? MainActivity)
            mainActivity?.changeBottomNavView(page)
        }

        with(binding) {
            mapListBtnHomeFragment.setOnClickListener {
                changePage(MainActivity.BottomNavPage.MapPage)
            }
            departmentsListBtnHomeFragment.setOnClickListener {
                changePage(MainActivity.BottomNavPage.DepartmentsPage)
            }
            scienceClubListBtnHomeFragment.setOnClickListener {
                changePage(MainActivity.BottomNavPage.ScienceClubsPage)
            }
        }
    }

    private fun setupSharedTransition() {
        sharedElementEnterTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
        sharedElementReturnTransition = TransitionInflater.from(context!!)
            .inflateTransition(R.transition.move)
    }

    private fun navigateToScienceClubDetails(scienceClub: ScienceClub) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToScienceClubsDetailsFragment(scienceClub)
        findNavController().navigate(action)
    }

    private fun navigateToBuilding(building: Building) {
        val action =
            HomeFragmentDirections.actionHomeFragmentToMapFragment()
        action.buildingToShow = building
        findNavController().navigate(action)

    }
}