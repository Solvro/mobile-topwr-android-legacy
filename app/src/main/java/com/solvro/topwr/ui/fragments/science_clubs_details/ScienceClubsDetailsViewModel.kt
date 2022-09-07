package com.solvro.topwr.ui.fragments.science_clubs_details

import androidx.lifecycle.*
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScienceClubsDetailsViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClub by lazy { MutableLiveData<ScienceClub>() }
    val scienceClub: LiveData<ScienceClub> by lazy { _scienceClub }

    private val _departmentName by lazy { MutableLiveData<String?>() }
    val departmentName: LiveData<String?> by lazy { _departmentName }

    init {
        val scienceClub = savedStateHandle.get<ScienceClub>("science_club")
        _scienceClub.value = scienceClub
        getScienceClubDepartment(scienceClub?.department)
    }

    private fun getScienceClubDepartment(departmentNumber: Int?) {
        if (departmentNumber == null) {
            _departmentName.postValue(null)
            return
        }
        viewModelScope.launch {
            val department = mainRepository.getDepartment(departmentNumber)
            _departmentName.postValue(department.data?.name ?: "")
        }
    }
}