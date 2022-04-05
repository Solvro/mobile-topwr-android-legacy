package com.solvro.topwr.ui.fragments.map_page

import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {

    private val _buildings = repository.getMaps()
    val buildings: LiveData<Resource<List<Building>>> get() = _buildings

    private val _selectedBuilding = MutableLiveData<Building?>(null)
    val selectedBuilding: LiveData<Building?> get() = _selectedBuilding

    var searchText = MutableLiveData("")

    fun selectBuilding(building: Building) {
        _selectedBuilding.postValue(if (building != selectedBuilding.value) building else null)
    }
}