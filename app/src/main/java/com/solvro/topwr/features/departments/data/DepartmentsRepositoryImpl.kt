package com.solvro.topwr.features.departments.data

import androidx.paging.map
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.features.departments.domain.model.Department
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DepartmentsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : DepartmentsRepository {

    override suspend fun getDepartment(departmentNumber: Int): Resource<Department?> {
        return when (val resource = remoteDataSource.getDepartment(departmentNumber)) {
            is Resource.Error -> Resource.Error(resource.message)
            is Resource.Success -> Resource.Success(resource.data.firstOrNull()?.toDomain())
            is Resource.Loading -> Resource.Loading()
        }
    }

    override fun getDepartmentsPaged() = remoteDataSource.getPagedDepartments()
        .map { pagingData ->
            pagingData.map { it.toDomain() }
        }

    override suspend fun getScienceClubs(): Resource<List<ScienceClub>> =
        remoteDataSource.getScientificCircles().map { list ->
            list.map { it.toDomain() }
        }
}
