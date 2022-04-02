package com.solvro.topwr.ui.fragments.departments_details_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.solvro.topwr.data.model.scienceClubs.ScienceClubs
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DepartmentsDetailsViewModel @Inject constructor(
  repository: MainRepository
) : ViewModel() {

    private val _scienceClubs = repository.getScienceClubs()
    val scienceClubs: LiveData<Resource<List<ScienceClubs>>> get() = _scienceClubs

}