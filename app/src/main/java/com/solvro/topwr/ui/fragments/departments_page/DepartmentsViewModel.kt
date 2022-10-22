package com.solvro.topwr.ui.fragments.departments_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.ui.fragments.departments_page.domain.use_case.GetDepartments_UseCase
import com.solvro.topwr.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val getDepartmentsUseCase: GetDepartments_UseCase
) : ViewModel() {

    private var lastTextFilter: String = ""
    private var departmentsJob: Job? = null

    private val _departments by lazy { MutableLiveData<PagingData<Departments>>() }
    val departments: LiveData<PagingData<Departments>> by lazy { _departments }

    init {
        getDepartments()
    }

    private fun getDepartments() {
        departmentsJob?.cancel()
        departmentsJob = viewModelScope.launch {
            getDepartmentsUseCase()
                .cancellable()
                .cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    val filteredData = pagingData.filter {
                        if (lastTextFilter == "") true
                        else (it.name?.contains(lastTextFilter) ?: false)
                                || it.code?.lowercase()?.contains(lastTextFilter) ?: false
                    }
                    _departments.postValue(filteredData)
                }
        }
    }

    fun setTextFilter(textFilter: String) {
        viewModelScope.launch {
            delay(Constants.DEFAULT_DEBOUNCE_TIME_MS)
            lastTextFilter = textFilter.trim()
            withContext(Dispatchers.Main){
                getDepartments()
            }
        }
    }
}