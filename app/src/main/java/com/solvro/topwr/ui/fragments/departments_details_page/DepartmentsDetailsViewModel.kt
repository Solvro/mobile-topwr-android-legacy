package com.solvro.topwr.ui.fragments.departments_details_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.scienceClubs.ScienceClub
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.ui.fragments.home_page.HomeFragment
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DepartmentsDetailsViewModel @Inject constructor(
  repository: MainRepository,
  savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _scienceClubs = repository.getScienceClubs()
    val scienceClubs: LiveData<Resource<List<ScienceClub>>> get() = _scienceClubs

    val departments: LiveData<Departments?> = savedStateHandle.getLiveData("department_info",null)
    val prevFragment: LiveData<String> = savedStateHandle.getLiveData("prevFragment", HomeFragment::class.java.name)

}