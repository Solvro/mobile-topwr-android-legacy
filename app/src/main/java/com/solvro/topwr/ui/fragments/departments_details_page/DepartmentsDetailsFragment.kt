package com.solvro.topwr.ui.fragments.departments_details_page

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
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
import com.google.android.gms.maps.model.*
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
            departmentPosition.text = "${getString(R.string.PWR_name)}\n${departmentInfo?.addres?.replace(",", "\n")}"
            departmentDetailBuildingTextView.text = "${getString(R.string.building)} ${departmentInfo?.code} "

            //Brak numeru pokoju np: pokój 21 (tak jak jest w Figmie) ??
            //Brak numerów telefonów ??
            val gradientFirst = Color.parseColor(departmentInfo?.color?.gradientFirst)
            val gradientSecond = Color.parseColor(departmentInfo?.color?.gradientSecond)

            val gradient = GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT,
                intArrayOf(gradientFirst, gradientSecond)
                )

            departmentDetailFragmentLogo.background = gradient
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
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker_1))
            )?.showInfoWindow()
        }
    }


}