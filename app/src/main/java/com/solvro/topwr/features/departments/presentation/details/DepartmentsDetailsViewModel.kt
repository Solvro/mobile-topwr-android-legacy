package com.solvro.topwr.features.departments.presentation.details

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.features.departments.domain.model.Departments
import com.solvro.topwr.features.departments.domain.use_case.GetScienceClubsDetailsParams
import com.solvro.topwr.features.departments.domain.use_case.GetScienceClubsUseCase
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepartmentsDetailsViewModel @Inject constructor(
    private val getScienceClubsUseCase: GetScienceClubsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClubs by lazy {
        MutableLiveData<PagingData<ScienceClub>>()
            .also {
                getScienceClubs(it)
            }
    }
    val scienceClubs: LiveData<PagingData<ScienceClub>> by lazy { _scienceClubs }

    val departments: LiveData<Departments?> = savedStateHandle.getLiveData("department_info", null)
    val prevFragment: LiveData<String> =
        savedStateHandle.getLiveData("prevFragment", HomeFragment::class.java.name)

    private fun getScienceClubs(scienceClubsLiveData: MutableLiveData<PagingData<ScienceClub>>) {
        getScienceClubsUseCase(
            GetScienceClubsDetailsParams(departments.value?.displayOrder),
            scope = viewModelScope
        ) {

        }
    }

    fun retryGetScienceClubs() {
        getScienceClubs(_scienceClubs)
    }
}