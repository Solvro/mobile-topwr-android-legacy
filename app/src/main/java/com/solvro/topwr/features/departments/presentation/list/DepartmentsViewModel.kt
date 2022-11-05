package com.solvro.topwr.features.departments.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.solvro.topwr.features.departments.domain.model.Departments
import com.solvro.topwr.features.departments.domain.use_case.GetDepartmentsParams
import com.solvro.topwr.features.departments.domain.use_case.GetDepartmentsUseCase
import com.solvro.topwr.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class DepartmentsViewModel @Inject constructor(
    private val getDepartmentsUseCase: GetDepartmentsUseCase
) : ViewModel() {

    private var lastTextFilter: String = ""

    private val _departments by lazy { MutableLiveData<PagingData<Departments>>() }
    val departments: LiveData<PagingData<Departments>> by lazy { _departments }

    init {
        getDepartments()
    }

    private fun getDepartments() {
        getDepartmentsUseCase(
            GetDepartmentsParams(lastTextFilter.lowercase()),
            scope = viewModelScope
        ) { result ->
            _departments.postValue(result)
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