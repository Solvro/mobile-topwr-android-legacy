package com.solvro.topwr.features.scienceclub.domain.usecase

import com.solvro.topwr.core.api.Resource
import com.solvro.topwr.core.base.BaseUseCase
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.repository.MainRepository
import javax.inject.Inject

class GetScienceClubDepartmentUseCase @Inject constructor(
    private val mainRepository: MainRepository
) : BaseUseCase<Int, Resource<Departments?>>() {
    override suspend fun action(params: Int): Resource<Departments?> {
        return mainRepository.getDepartment(params)
    }
}