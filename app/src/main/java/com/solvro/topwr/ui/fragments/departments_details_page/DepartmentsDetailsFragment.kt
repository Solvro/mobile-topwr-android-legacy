package com.solvro.topwr.ui.fragments.departments_details_page

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.solvro.topwr.R
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.databinding.DepartmentsDetailsFragmentBinding
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.solvro.topwr.ui.adapters.FieldsOfStudyAdapter
import com.solvro.topwr.ui.adapters.PhoneAdapter
import com.solvro.topwr.ui.adapters.ScienceClubsAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.view.MotionEvent




@AndroidEntryPoint
class DepartmentsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsDetailsFragment()
    }

    private lateinit var binding: DepartmentsDetailsFragmentBinding
    private val viewModel: DepartmentsDetailsViewModel by viewModels()
    private val args: DepartmentsDetailsFragmentArgs by navArgs()

    private var map: GoogleMap? = null
    private var departmentInfo: Departments? = null

    private lateinit var scienceClubsAdapter: ScienceClubsAdapter
    private lateinit var fieldsOfStudyAdapter: FieldsOfStudyAdapter
    private lateinit var phoneAdapter: PhoneAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DepartmentsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        departmentInfo = args.departmentInfo;

        setupView()
        allowMapToMove()
        setupMap()
        setupPhoneNumbers()
        setupFieldsOfStudy()
        setupScientificCircles()
    }

    private fun setupView(){

        binding.apply {

            backToMainDepartmentBtn.setOnClickListener {
                findNavController().navigateUp()
            }

            departmentName.text = departmentInfo?.name
            departmentPosition.text = "${getString(R.string.PWR_name)}\n${departmentInfo?.addres?.replace(",", "")}"
            departmentDetailBuildingTextView.text = "${getString(R.string.building)} ${departmentInfo?.code} "

            //Albo nie widzę gdzie to jest, albo:
            //Brak numeru pokoju np: pokój 21 (tak jak jest w Figmie) ??
            //Brak numerów telefonów ??

            Glide.with(binding.root.context)
                .load(departmentInfo?.logo?.url)
                .into(departmentDetailFragmentLogo)

            val gradientFirst = Color.parseColor(departmentInfo?.color?.gradientFirst)
            val gradientSecond = Color.parseColor(departmentInfo?.color?.gradientSecond)

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(gradientSecond, gradientFirst)
            )

            departmentDetailFragmentLogo.background = gradient



        }
    }

    /*This function allow user to move map vertically, even though we are in scrollview*/
    @SuppressLint("ClickableViewAccessibility")
    private fun allowMapToMove(){
        binding.transparentMapOverlapping.setOnTouchListener { v, event ->
            return@setOnTouchListener when(event.action){
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

    private fun setupMap(){

        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.departmentMap) as SupportMapFragment

        //val marker = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_1)

        mapFragment.getMapAsync {
            map = it
            val position = LatLng(departmentInfo?.latitude!!, departmentInfo?.longitude!!)

            map?.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    position,17f
                )
            )

            map?.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(departmentInfo?.name)
            )

        }
    }

    private fun setupPhoneNumbers(){

        //where phone numbers?
        //departmentInfo?.

        phoneAdapter = PhoneAdapter(listOf("(+48) 71 320 62 30", "(+48) 71 320 62 30")){
            phoneNumber -> null
        }

        binding.contactPhoneRecyclerView.apply {
            adapter = phoneAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setupFieldsOfStudy(){

        departmentInfo?.fieldsOfStudy?.let {
            fieldsOfStudyAdapter = FieldsOfStudyAdapter(it)
        }

        binding.coursesOfStudyRecyclerView.apply {
            adapter = fieldsOfStudyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun setupScientificCircles(){

        binding.scienceClubListTextView.setOnClickListener {
            val action = DepartmentsDetailsFragmentDirections.actionDepartmentsDetailsFragmentToScienceClubsFragment()
            findNavController().navigate(action)
        }

        viewModel.scienceClubs.observe(viewLifecycleOwner){
            it?.data.let { scienceClubs ->
                scienceClubs?.let {
                    scienceClubsAdapter = ScienceClubsAdapter(scienceClubs.filter { scienceClubs ->

                        if (scienceClubs.id != null){
                            departmentInfo?.scientificCircles?.contains(scienceClubs.id) == true
                        }else{
                            false
                        }

                    }) { scienceClubItem ->
                        Toast.makeText(context, scienceClubItem.name, Toast.LENGTH_SHORT).show()
                        //TODO("Navigate to specific club")
                    }

                    binding.scienceClubsOfDepartmentRecyclerView.apply {
                        adapter = scienceClubsAdapter
                        layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    }

                }
            }
        }




    }



}