package com.solvro.topwr.features.departments_page.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.features.departments_page.domain.repository.DepartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDepartments_UseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    operator fun invoke(): Flow<PagingData<DepartmentsRemote>> {
        return departmentRepository.getDepartmentsPaged()
    }
}