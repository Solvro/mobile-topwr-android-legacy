package com.solvro.topwr.features.map.presentation.map

import androidx.lifecycle.*
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.map.domain.model.Building
import com.solvro.topwr.features.map.domain.usecase.AddIdToBuildingsSearchHistoryUseCase
import com.solvro.topwr.features.map.domain.usecase.GetBuildingUseCase
import com.solvro.topwr.features.map.domain.usecase.GetBuildingsSearchHistoryUseCase
import com.solvro.topwr.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getBuildingUseCase: GetBuildingUseCase,
    private val getBuildingsSearchHistoryUseCase: GetBuildingsSearchHistoryUseCase,
    private val addIdToBuildingsSearchHistoryUseCase: AddIdToBuildingsSearchHistoryUseCase,
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

    private val _searchHistory: MutableLiveData<List<Int>> = MutableLiveData()
    val searchHistory: LiveData<List<Int>> get() = _searchHistory

    private var textFilter = ""

    init {
        _selectedBuilding.value = Event(savedStateHandle.get<Building>("buildingToShow"))
        getBuildingsSearchHistoryUseCase(params = null, scope = viewModelScope){
            _searchHistory.postValue(it)
        }
    }

    fun selectBuilding(building: Building) {
        val event =
            if (building != selectedBuilding.value?.peekContent()) Event(building) else Event(null)
        _selectedBuilding.postValue(event)
        building.id?.let {
            addIdToBuildingsSearchHistoryUseCase(it, viewModelScope) {}
        }
        //refresh search history
        getBuildingsSearchHistoryUseCase(params = null, scope = viewModelScope) {
            _searchHistory.postValue(it)
        }
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
            getBuildingUseCase(null, viewModelScope) { buildingsResource ->
                when(buildingsResource) {
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
}
