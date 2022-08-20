package com.solvro.topwr.ui.fragments.departments_details_page

import androidx.lifecycle.*
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepartmentsDetailsViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClubs = MutableLiveData<Resource<List<ScienceClub>>>()
    val scienceClubs: LiveData<Resource<List<ScienceClub>>> by lazy { _scienceClubs }

    val departments: LiveData<Departments?> = savedStateHandle.getLiveData("department_info", null)
    val prevFragment: LiveData<String> =
        savedStateHandle.getLiveData("prevFragment", HomeFragment::class.java.name)

    init {
        getScienceClubs()
    }

    private fun getScienceClubs() {
        viewModelScope.launch { repository.getScienceClubs() }
    }
}