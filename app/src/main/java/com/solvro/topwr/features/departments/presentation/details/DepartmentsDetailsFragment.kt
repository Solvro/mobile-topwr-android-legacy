package com.solvro.topwr.features.departments.presentation.details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.solvro.topwr.R
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.databinding.DepartmentsDetailsFragmentBinding
import com.solvro.topwr.ui.adapters.DefaultLoadStateAdapter
import com.solvro.topwr.ui.adapters.FieldsOfStudyAdapter
import com.solvro.topwr.ui.adapters.ScienceClubBigAdapter
import com.solvro.topwr.features.departments.presentation.list.DepartmentsFragment
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import com.solvro.topwr.ui.fragments.science_clubs_page.ScienceClubComparator
import com.solvro.topwr.ui.fragments.science_clubs_page.ScienceClubsFragment
import com.solvro.topwr.utils.DrawableToBitmapDescriptorConverter
import com.solvro.topwr.utils.withLoadStateAdapters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DepartmentsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsDetailsFragment()
    }

    private lateinit var binding: DepartmentsDetailsFragmentBinding
    private val viewModel: DepartmentsDetailsViewModel by viewModels()
    private var map: GoogleMap? = null
    private val scienceClubsAdapter =
        ScienceClubBigAdapter(ScienceClubComparator) { scienceClubItem ->
            navigateToScienceClub(scienceClubItem)
        }
    private lateinit var fieldsOfStudyAdapter: FieldsOfStudyAdapter
    private var infoAdapter = DepartmentInfoAdapter { phoneNumber ->
        Intent(Intent.ACTION_DIAL).let {
            it.data = Uri.parse("tel:+48${phoneNumber.replace(" ", "")}")
            startActivity(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = com.solvro.topwr.databinding.DepartmentsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        allowMapToMove()
        setupMap()
        setupPhoneNumbers()
        setupFieldsOfStudy()
        setupScientificCircles()
    }

    private fun setupView() {

        binding.apply {
            backToMainDepartmentBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            //when navigating add prevFragment arg to get custom backBtn name
            viewModel.prevFragment.observe(viewLifecycleOwner) { prevFragment ->
                when (prevFragment) {
                    HomeFragment::class.java.name ->
                        getString(R.string.main_page)
                    DepartmentsFragment::class.java.name ->
                        getString(R.string.departments)
                    ScienceClubsFragment::class.java.name ->
                        getString(R.string.science_clubs)
                    else ->
                        prevFragment
                    /*Science club details can be example here.
                                Button should look like:  < KN Solvro */
                }.also { backToMainDepartmentBtn.text = it }
            }

            viewModel.departments.observe(viewLifecycleOwner) {
                if (it != null) {
                    departmentName.text = it.name
                    departmentPosition.text =
                        getString(R.string.department_position, it.addres?.replace(",", ""))
                    departmentDetailBuildingTextView.text =
                        getString(R.string.department_detail_building, it.code)

                    Glide.with(binding.root.context)
                        .load(it.logo?.url)
                        .into(departmentDetailFragmentLogo)
                    val gradientFirst = Color.parseColor(it.color?.gradientFirst)
                    val gradientSecond = Color.parseColor(it.color?.gradientSecond)
                    val gradient = GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        intArrayOf(gradientSecond, gradientFirst)
                    )
                    departmentDetailFragmentLogo.background = gradient
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.scienceClubsOfDepartmentRecyclerView.apply {
            adapter = scienceClubsAdapter.withLoadStateAdapters(
                DefaultLoadStateAdapter(viewModel::retryGetScienceClubs),
                DefaultLoadStateAdapter(viewModel::retryGetScienceClubs)
            )
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    /*This function allow user to move map vertically, even though we are in scrollview*/
    @SuppressLint("ClickableViewAccessibility")
    private fun allowMapToMove() {
        binding.transparentMapOverlapping.setOnTouchListener { v, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.root.requestDisallowInterceptTouchEvent(true)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.root.requestDisallowInterceptTouchEvent(false)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    binding.root.requestDisallowInterceptTouchEvent(true)
                    false
                }
                else -> true
            }
        }
    }

    private fun setupMap() {
        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.departmentMap) as SupportMapFragment

        mapFragment.getMapAsync {
            map = it
            viewModel.departments.observe(viewLifecycleOwner) { departments ->
                val position = LatLng(departments?.latitude!!, departments?.longitude!!)

                map?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        position, 17f
                    )
                )
                map?.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(departments.name)
                        .icon(
                            DrawableToBitmapDescriptorConverter.vectorToBitmap(
                                resources,
                                R.drawable.ic_map_marker_1
                            )
                                ?: BitmapDescriptorFactory.defaultMarker()
                        )
                )
            }
        }
    }

    private fun setupPhoneNumbers() {
        viewModel.departments.observe(viewLifecycleOwner) { departments ->
            infoAdapter.addData(departments?.infoSection?.firstOrNull()?.info ?: listOf())
            binding.contactPhoneRecyclerView.apply {
                adapter = infoAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupFieldsOfStudy() {
        fieldsOfStudyAdapter = FieldsOfStudyAdapter(mutableListOf())

        viewModel.departments.observe(viewLifecycleOwner) {
            it?.fieldsOfStudy?.let { fieldsOfStudy ->
                fieldsOfStudyAdapter.updateList(fieldsOfStudy)
            }
        }

        binding.coursesOfStudyRecyclerView.apply {
            adapter = fieldsOfStudyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupScientificCircles() {

        binding.scienceClubListTextView.setOnClickListener {
            val action =
                com.solvro.topwr.features.departments.presentation.details.DepartmentsDetailsFragmentDirections.actionDepartmentsDetailsFragmentToScienceClubsFragment()
            findNavController().navigate(action)
        }

        viewModel.scienceClubs.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                scienceClubsAdapter.submitData(it)
            }
        }
    }

    private fun navigateToScienceClub(scienceClub: ScienceClub) {
        val action =
            com.solvro.topwr.features.departments.presentation.details.DepartmentsDetailsFragmentDirections.actionDepartmentsDetailsFragmentToScienceClubsDetailsFragment(
                scienceClub
            )
        findNavController().navigate(action)
    }
}