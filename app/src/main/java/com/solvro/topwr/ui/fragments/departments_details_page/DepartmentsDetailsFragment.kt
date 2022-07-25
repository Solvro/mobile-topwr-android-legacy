package com.solvro.topwr.ui.fragments.departments_details_page

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.solvro.topwr.R
import com.solvro.topwr.databinding.DepartmentsDetailsFragmentBinding
import androidx.navigation.fragment.findNavController
import com.solvro.topwr.ui.adapters.FieldsOfStudyAdapter
import com.solvro.topwr.ui.adapters.PhoneAdapter
import com.solvro.topwr.ui.adapters.ScienceClubsAdapter
import dagger.hilt.android.AndroidEntryPoint
import android.view.MotionEvent
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat.getDrawable
import com.solvro.topwr.ui.fragments.departments_page.DepartmentsFragment
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import com.solvro.topwr.ui.fragments.science_clubs_page.ScienceClubsFragment

@AndroidEntryPoint
class DepartmentsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DepartmentsDetailsFragment()
    }

    private lateinit var binding: DepartmentsDetailsFragmentBinding
    private val viewModel: DepartmentsDetailsViewModel by viewModels()
    private var map: GoogleMap? = null
    private lateinit var scienceClubsAdapter: ScienceClubsAdapter
    private lateinit var fieldsOfStudyAdapter: FieldsOfStudyAdapter
    private lateinit var phoneAdapter: PhoneAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DepartmentsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

            //when navigating add prevFragment arg to get custom backBtn name
            viewModel.prevFragment.observe(viewLifecycleOwner) {
                prevFragment ->
                when(prevFragment) {
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

            viewModel.departments.observe(viewLifecycleOwner){

                if (it != null) {
                    departmentName.text = it.name
                    departmentPosition.text = getString(R.string.department_position, it.addres?.replace(",", "") )
                    departmentDetailBuildingTextView.text = getString(R.string.department_detail_building, it.code)

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
        MapsInitializer.initialize(requireContext().applicationContext, )
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.departmentMap) as SupportMapFragment

        mapFragment.getMapAsync {
            map = it
            viewModel.departments.observe(viewLifecycleOwner) { departments ->
                val position = LatLng(departments?.latitude!!, departments?.longitude!!)

                map?.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        position,17f
                    )
                )
                map?.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title(departments?.name)
                        .icon(vectorToBitmap(R.drawable.ic_map_marker_1))
                )
            }
        }
    }

    private fun setupPhoneNumbers(){
        viewModel.departments.observe(viewLifecycleOwner){ departments ->
            val phones = mutableListOf<String>()
            departments?.infoSection?.forEach {
                infoSection ->
                infoSection.info?.forEach {
                    if(it.type == "PhoneNumber"){
                        it.value?.let { number -> phones.add(number) }
                    }
                }
            }

            phoneAdapter = PhoneAdapter(phones) { phoneNumber ->
                Intent(Intent.ACTION_DIAL).let {
                    it.data = Uri.parse("tel:+48${phoneNumber.replace(" ", "")}")
                    startActivity(it)
                }
            }

            binding.contactPhoneRecyclerView.apply {
                adapter = phoneAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun setupFieldsOfStudy(){
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
                            viewModel.departments.value?.scientificCircles?.contains(scienceClubs.id) == true
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


    private fun vectorToBitmap(@DrawableRes id : Int): BitmapDescriptor {

        val vector: Drawable = getDrawable(resources, id, null) ?: return BitmapDescriptorFactory.defaultMarker()

        val bitmap = Bitmap.createBitmap(vector.intrinsicWidth,
            vector.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vector.setBounds(0, 0, canvas.width, canvas.height)
        vector.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}