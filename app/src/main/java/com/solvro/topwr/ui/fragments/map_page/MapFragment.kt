package com.solvro.topwr.ui.fragments.map_page

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
import com.solvro.topwr.core.api.Resource
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.databinding.MapFragmentBinding
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.DrawableToBitmapDescriptorConverter
import com.solvro.topwr.utils.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var binding: MapFragmentBinding
    private lateinit var adapter: BuildingsAdapter
    private var map: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var bottomSheet: BottomSheetBehavior<ConstraintLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        bottomSheet = BottomSheetBehavior.from(binding.mapBottomSheet.bottomMapSheet)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        setupRecyclerView()
        setupMaps()
        setObservers()
        setListeners()
    }

    private fun bindViewModel() {
        binding.viewModel = viewModel
    }

    private fun setupMaps() {
        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            map = it
            if (viewModel.selectedBuilding.value?.peekContent() != null) {
                setBuildingMarker(viewModel.selectedBuilding.value?.peekContent())
            } else {
                it.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(Constants.DEFAULT_MAP_LATITUDE, Constants.DEFAULT_MAP_LONGITUDE),
                        Constants.DEFAULT_CAMERA_ZOOM
                    )
                )
            }
        }
    }

    private fun setupRecyclerView() {
        adapter = BuildingsAdapter { building ->
            viewModel.selectBuilding(building)
        }
        binding.mapBottomSheet.buildingsRecyclerView.apply {
            adapter = this@MapFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setObservers() {
        viewModel.apply {
            buildings.observe(viewLifecycleOwner) {
                setBottomSheetLoadingView(it is Resource.Loading)
                when (it) {
                    is Resource.Success -> it.data?.let { data -> adapter.addItems(data) }

                    is Resource.Error -> Toast.makeText(
                        requireContext(),
                        requireContext().getString(R.string.data_error),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }

            selectedBuilding.observe(viewLifecycleOwner) {
                binding.mapBottomSheet.navigateMeButton.isVisible = it.peekContent() != null
                val selectedBuilding = it.getContentIfNotHandled()
                setBuildingMarker(selectedBuilding)
                adapter.setSelectedBuilding(selectedBuilding)
                setBottomSheetState(true)
                bottomSheet.peekHeight = if (it.peekContent() == null) {
                    BOTTOM_SHEET_PEEK_HEIGHT_COLLAPSED
                } else BOTTOM_SHEET_PEEK_HEIGHT_EXTENDED
                setBottomSheetState(false)
                lifecycleScope.launch {
                    delay(100)
                    binding.mapBottomSheet.buildingsRecyclerView.smoothScrollBy(0, Int.MIN_VALUE)
                }
            }

            searchHistory.observe(viewLifecycleOwner) {
                adapter.setSearchHistory(it)
            }
        }
    }

    private fun setBottomSheetLoadingView(isLoading: Boolean) {
        binding.mapBottomSheet.apply {
            bottomMapSheetProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            buildingsRecyclerView.visibility = if (!isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setListeners() {
        with(binding) {
            with(mapBottomSheet) {
                buildingsSearchBar.setOnQueryTextListener(object :
                    SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean = false

                    override fun onQueryTextChange(text: String?): Boolean {
                        setBottomSheetState(isExpanded = true)
                        this@MapFragment.viewModel.setTextFilter(text ?: "")
                        return false
                    }
                })
                navigateMeButton.setOnClickListener {
                    this@MapFragment.viewModel.selectedBuilding.value?.peekContent()?.let {
                        if (it.latitude != null && it.longitude != null)
                            navigateToMaps(it.latitude, it.longitude)
                    }
                }
            }
        }
    }

    private fun setBottomSheetState(isExpanded: Boolean) {
        bottomSheet.apply {
            state =
                if (isExpanded) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun setBuildingMarker(building: Building?) {
        currentMarker?.remove()
        if (building == null) return

        if (building.latitude != null && building.longitude != null) {
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
                    .icon(
                        DrawableToBitmapDescriptorConverter.vectorToBitmap(
                            resources,
                            R.drawable.ic_building_map_marker
                        )
                    )
            )
            currentMarker?.showInfoWindow()
        }
    }

    private fun navigateToMaps(lat: Double, lng: Double) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://maps.google.com/maps?&daddr=${lat},${lng}")
        )
        startActivity(intent)
    }

    companion object {
        fun newInstance() = MapFragment()
        val BOTTOM_SHEET_PEEK_HEIGHT_COLLAPSED = 140.toPx
        val BOTTOM_SHEET_PEEK_HEIGHT_EXTENDED = 280.toPx
    }
}