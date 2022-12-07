package com.solvro.topwr.features.scienceclub.presentation.details

import androidx.lifecycle.*
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.features.scienceclub.domain.usecase.GetScienceClubDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScienceClubsDetailsViewModel @Inject constructor(
    private val getScienceClubDepartmentUseCase: GetScienceClubDepartmentUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClub by lazy { MutableLiveData<ScienceClub>() }
    val scienceClub: LiveData<ScienceClub> by lazy { _scienceClub }

    private val _departmentName by lazy { MutableLiveData<String?>(null) }
    val departmentName: LiveData<String?> by lazy { _departmentName }

    init {
        val scienceClub = savedStateHandle.get<ScienceClub>("science_club")
        _scienceClub.value = scienceClub
        getScienceClubDepartment(scienceClub?.departmentNumber)
    }

    private fun getScienceClubDepartment(departmentNumber: Int?) {
        departmentNumber?.let {
            getScienceClubDepartmentUseCase(departmentNumber, viewModelScope) {
                _departmentName.postValue(it.data?.name)
            }
        }
    }
}