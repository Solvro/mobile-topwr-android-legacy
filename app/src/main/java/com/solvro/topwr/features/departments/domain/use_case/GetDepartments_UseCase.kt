package com.solvro.topwr.features.departments.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDepartments_UseCase @Inject constructor(
    private val departmentsRepository: DepartmentsRepository
) {
    operator fun invoke(): Flow<PagingData<DepartmentsRemote>> {
        return departmentsRepository.getDepartmentsPaged()
    }
}