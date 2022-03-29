package com.solvro.topwr.ui.fragments.map_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.solvro.topwr.R
import com.solvro.topwr.databinding.MapFragmentBinding
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment() {

    private val viewModel: MapViewModel by viewModels()
    private lateinit var binding: MapFragmentBinding
    private lateinit var adapter: BuildingsAdapter

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
        viewModel.buildings.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data -> adapter.addData(data) }
                }
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Błąd wczytywania danych", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun setupMaps() {
        MapsInitializer.initialize(requireContext().applicationContext)
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.1087, 17.0591), 17f))
        }
    }

    private fun setupRecyclerView() {
        val rw = binding.mapBottomSheet.buildingsRecyclerView
        adapter = BuildingsAdapter()
        rw.adapter = adapter
        rw.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
        fun newInstance() = MapFragment()
    }
}