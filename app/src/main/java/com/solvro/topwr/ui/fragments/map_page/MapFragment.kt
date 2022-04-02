package com.solvro.topwr.ui.fragments.map_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.solvro.topwr.R
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.MapFragmentBinding
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.Resource
import com.solvro.topwr.utils.toPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var binding: MapFragmentBinding
    private lateinit var adapter: BuildingsAdapter
    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupMaps()
        setObservers()
    }

    private fun setObservers() {
        viewModel.apply {
            buildings.observe(viewLifecycleOwner) {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        it.data?.let { data -> adapter.addItems(data) }
                    }
                    Resource.Status.LOADING -> {}
                    Resource.Status.ERROR -> {
                        Toast.makeText(
                            requireContext(),
                            requireContext().getString(R.string.data_error),
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            }
            selectedBuilding.observe(viewLifecycleOwner) {
                setBuildingMarker(it)
                adapter.setSelectedBuilding(it)
                BottomSheetBehavior.from(binding.mapBottomSheet.root).apply {
                    peekHeight = if (it == null) {
                        140.toPx
                    } else 280.toPx
                    state =
                        BottomSheetBehavior.STATE_COLLAPSED
                }
            }
        }
    }

    private fun setupMaps() {
        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
            it.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(Constants.DEFAULT_MAP_LATITUDE, Constants.DEFAULT_MAP_LONGITUDE),
                    Constants.DEFAULT_CAMERA_ZOOM
                )
            )
        }
    }

    private fun setupRecyclerView() {
        val rw = binding.mapBottomSheet.buildingsRecyclerView
        adapter = BuildingsAdapter { building ->
            if (BottomSheetBehavior.from(binding.mapBottomSheet.root).state
                == BottomSheetBehavior.STATE_EXPANDED)
                viewModel.selectBuilding(building)
        }
        rw.adapter = adapter
        rw.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setBuildingMarker(building: Building?) {
        currentMarker?.remove()
        if (building == null) return

        val newLocation = LatLng(
            building.latitude,
            building.longitude
        )
        val cameraPosition = CameraPosition.Builder()
            .target(newLocation)
            .zoom(Constants.DEFAULT_CAMERA_ZOOM)
            .build()
        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        currentMarker = map?.addMarker(
            MarkerOptions()
                .position(newLocation)
                .title(building.name)
        )
        currentMarker?.showInfoWindow()
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}