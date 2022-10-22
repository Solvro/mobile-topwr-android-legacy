package com.solvro.topwr.ui.fragments.departments_page.domain.use_case

import androidx.paging.PagingData
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.ui.fragments.departments_page.domain.repository.DepartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetDepartments_UseCase @Inject constructor(
    private val departmentRepository: DepartmentRepository
) {
    operator fun invoke(): Flow<PagingData<Departments>> {
        return departmentRepository.getDepartmentsPaged()
    }
}