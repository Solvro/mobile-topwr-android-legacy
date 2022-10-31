package com.solvro.topwr.features.scienceclub.domain.usecase

import com.solvro.topwr.core.api.Resource
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.repository.MainRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetScienceClubDepartmentUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    operator fun invoke(
        departmentNumber: Int,
        scope: CoroutineScope,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        onResult: (Resource<Departments?>) -> Unit
    ) {
        scope.launch(dispatcher) {
            val department = mainRepository.getDepartment(departmentNumber)
            onResult(department)
        }
    }
}