package com.solvro.topwr.features.departments.presentation.details

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solvro.topwr.features.departments.domain.model.Department
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.features.scienceclub.domain.usecase.GetScienceClubUseCase
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DepartmentsDetailsViewModel @Inject constructor(
    private val getAllScienceClubsUseCase: GetScienceClubUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClubs by lazy {
        MutableLiveData<PagingData<ScienceClub>>()
            .also {
                getScienceClubs(it)
            }
    }
    val scienceClubs: LiveData<PagingData<ScienceClub>> by lazy {
        _scienceClubs.cachedIn(
            viewModelScope
        )
    }

    val departments: LiveData<Department?> = savedStateHandle.getLiveData("department_info", null)
    val prevFragment: LiveData<String> =
        savedStateHandle.getLiveData("prevFragment", HomeFragment::class.java.name)

    private fun getScienceClubs(scienceClubsLiveData: MutableLiveData<PagingData<ScienceClub>>) {
        departments.value?.displayOrder?.let {
            getAllScienceClubsUseCase(
                it,
                scope = viewModelScope
            ) { result ->
                scienceClubsLiveData.postValue(result)
            }
        }
    }

    fun retryGetScienceClubs() {
        getScienceClubs(_scienceClubs)
    }
}
