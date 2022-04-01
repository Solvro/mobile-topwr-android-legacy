package com.solvro.topwr.ui.fragments.departments_details_page

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.solvro.topwr.R
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.databinding.DepartmentsDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DepartmentsDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.contactPhoneRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        departmentInfo = args.departmentInfo;

        binding.apply {
            departmentName.text = departmentInfo?.name
            departmentPosition.text = departmentInfo?.addres
            departmentDetailBuildingTextView.text = departmentInfo?.locale
        }

        setupMap()

    }

    private fun setupMap(){
        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.departmentMap) as SupportMapFragment

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
            )?.showInfoWindow()
        }
    }


}