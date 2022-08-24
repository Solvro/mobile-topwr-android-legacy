package com.solvro.topwr.ui.fragments.departments_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _departments by lazy { MutableLiveData<PagingData<Departments>>() }
    val departments: LiveData<PagingData<Departments>> by lazy { _departments }

    init {
        getDepartments()
    }

    private fun getDepartments() {
        viewModelScope.launch {
            repository.getDepartmentsPaged()
                .cancellable()
                .cachedIn(viewModelScope)
                .collectLatest {
                    _departments.postValue(it)
                }
        }
    }
}