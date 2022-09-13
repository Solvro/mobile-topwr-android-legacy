package com.solvro.topwr.ui.fragments.map_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private var allBuildingsCached = mutableListOf<Building>()

    private val _buildings by lazy {
        MutableLiveData<Resource<List<Building>>>()
            .also { getBuildings(it) }
    }
    val buildings: LiveData<Resource<List<Building>>> by lazy { _buildings }

    private val _selectedBuilding = MutableLiveData<Building?>(null)
    val selectedBuilding: LiveData<Building?> get() = _selectedBuilding

    private val _searchHistory = MutableLiveData(repository.getBuildingsSearchHistory())
    val searchHistory: LiveData<List<Int>> get() = _searchHistory

    private var textFilter = ""

    fun selectBuilding(building: Building) {
        _selectedBuilding.postValue(if (building != selectedBuilding.value) building else null)
        building.id?.let {
            repository.addIdToBuildingsSearchHistory(it)
        }
        //refresh search history
        _searchHistory.postValue(repository.getBuildingsSearchHistory())
    }

    fun setTextFilter(filter: String) {
        textFilter = filter.lowercase().trim()
        _buildings.postValue(Resource.success(allBuildingsCached.filter {
            it.name?.lowercase()?.contains(filter) ?: false
        }))
    }

    private fun getBuildings(buildingsLiveData: MutableLiveData<Resource<List<Building>>>) {
        buildingsLiveData.postValue(Resource.loading())
        viewModelScope.launch {
            val buildingsResource = repository.getBuildings()
            when (buildingsResource.status) {
                Resource.Status.SUCCESS -> {
                    buildingsResource.data?.let {
                        if (allBuildingsCached.isNotEmpty()) allBuildingsCached.clear()
                        allBuildingsCached.addAll(buildingsResource.data)
                        setTextFilter(textFilter)
                    }
                }
                Resource.Status.ERROR -> {
                    _buildings.postValue(buildingsResource)
                }
                Resource.Status.LOADING -> {}
            }
        }
    }
}
