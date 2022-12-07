package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.model.Department
import com.solvro.topwr.utils.Constants
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) : FlowUseCase<GetDepartmentsParams, PagingData<Department>>() {

    private var getDepartmentsJob: Job? = null

    override suspend fun action(params: GetDepartmentsParams): Flow<PagingData<Department>> {
        getDepartmentsJob?.cancel()
        delay(Constants.DEFAULT_DEBOUNCE_TIME_MS)
        return departmentsRepository.getDepartmentsPaged()
            .cancellable()
            .transform {
                val filteredData = it.filter { department ->
                    if (params.textFilter == "")    true
                    else (department.name?.contains(params.textFilter) ?: false)
                            || department.code?.lowercase()?.contains(params.textFilter) ?: false
                }
                emit(filteredData)
            }
    }
}

data class GetDepartmentsParams(
    val textFilter: String
)