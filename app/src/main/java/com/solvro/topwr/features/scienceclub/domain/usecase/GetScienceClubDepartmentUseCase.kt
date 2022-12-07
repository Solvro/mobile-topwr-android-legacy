package com.solvro.topwr.features.scienceclub.domain.usecase

import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.data.repository.MainRepository
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.model.Department
import javax.inject.Inject

class GetScienceClubDepartmentUseCase @Inject constructor(
    private val repository: DepartmentsRepository
) : BaseUseCase<Int, Resource<Department?>>() {
    override suspend fun action(params: Int): Resource<Department?> {
        return repository.getDepartment(params)
    }
}
