package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import androidx.paging.filter
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.model.Departments
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) : FlowUseCase<GetDepartmentsParams, PagingData<Departments>>() {

    private var getDepartmentsJob: Job? = null

    override suspend fun action(params: GetDepartmentsParams): Flow<PagingData<Departments>> {
        getDepartmentsJob?.cancel()
        return departmentsRepository.getDepartmentsPaged()
            .cancellable()
            .transform {
                val filteredData = it.filter { department ->
                    if (params.textFilter.lowercase() == "")    true
                    else (department.name?.contains(params.textFilter.lowercase()) ?: false)
                            || department.code?.lowercase()?.contains(params.textFilter.lowercase()) ?: false
                }
                emit(filteredData)
            }
    }
}

data class GetDepartmentsParams(
    val textFilter: String
)