package com.solvro.topwr.ui.fragments.map_page

import androidx.lifecycle.*
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var allBuildingsCached = mutableListOf<Building>()

    private val _buildings by lazy {
        MutableLiveData<Resource<List<Building>>>()
            .also { getBuildings(it) }
    }
    val buildings: LiveData<Resource<List<Building>>> by lazy { _buildings }

    private val _selectedBuilding = MutableLiveData<Event<Building?>>(Event(null))
    val selectedBuilding: LiveData<Event<Building?>> get() = _selectedBuilding

    private val _searchHistory = MutableLiveData(repository.getBuildingsSearchHistory())
    val searchHistory: LiveData<List<Int>> get() = _searchHistory

    private var textFilter = ""

    init {
        _selectedBuilding.value = Event(savedStateHandle.get<Building>("buildingToShow"))
    }

    fun selectBuilding(building: Building) {
        val event =
            if (building != selectedBuilding.value?.peekContent()) Event(building) else Event(null)
        _selectedBuilding.postValue(event)
        building.id?.let {
            repository.addIdToBuildingsSearchHistory(it)
        }
        //refresh search history
        _searchHistory.postValue(repository.getBuildingsSearchHistory())
    }

    fun setTextFilter(filter: String) {
        textFilter = filter.lowercase().trim()
        _buildings.postValue(Resource.Success(allBuildingsCached.filter {
            it.name?.lowercase()?.contains(filter) ?: false
        }))
    }

    private fun getBuildings(buildingsLiveData: MutableLiveData<Resource<List<Building>>>) {
        buildingsLiveData.postValue(Resource.Loading())
        viewModelScope.launch {
            when (val buildingsResource = repository.getBuildings()) {
                is Resource.Success -> {
                    buildingsResource.data.let {
                        if (allBuildingsCached.isNotEmpty()) allBuildingsCached.clear()
                        allBuildingsCached.addAll(buildingsResource.data)
                        setTextFilter(textFilter)
                    }
                }
                is Resource.Error -> {
                    _buildings.postValue(buildingsResource)
                }
                is Resource.Loading -> {}
            }
        }
    }
}
