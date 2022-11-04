package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.model.Departments
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) : FlowUseCase<Unit, PagingData<Departments>>() {

    override suspend fun action(params: Unit): Flow<PagingData<Departments>> {
        return departmentsRepository.getDepartmentsPaged()
    }
}