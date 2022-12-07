package com.solvro.topwr.features.departments.domain

import androidx.paging.PagingData
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.departments.domain.model.Department
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import kotlinx.coroutines.flow.Flow

interface DepartmentsRepository {
    suspend fun getScienceClubs(): Resource<List<ScienceClub>>
    suspend fun getDepartment(departmentNumber: Int): Resource<Department?>
    fun getDepartmentsPaged(): Flow<PagingData<Department>>
}
