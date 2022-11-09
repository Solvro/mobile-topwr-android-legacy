package com.solvro.topwr.features.departments.data

import androidx.paging.map
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.core.api.model.Resource
import com.solvro.topwr.features.departments.domain.model.Departments
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DepartmentsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DepartmentsRepository {

    override suspend fun getDepartment(departmentNumber: Int): Resource<Departments?> {
        val resource = remoteDataSource.getDepartment(departmentNumber)
        return when (resource.status) {
            Resource.Status.ERROR -> Resource.error(resource.message!!)
            Resource.Status.SUCCESS -> Resource.success(resource.data?.firstOrNull()?.toDomain())
            Resource.Status.LOADING -> Resource.loading()
        }
    }

    override fun getDepartmentsPaged() = remoteDataSource.getPagedDepartments()
        .map { pagingData ->
            pagingData.map { it.toDomain() }
    }

    override suspend fun getScienceClubs(): Resource<List<ScienceClub>> =
        remoteDataSource.getScientificCircles()

    override fun getScienceClubsPaged() = remoteDataSource.getPagedScientificCircles()

    override suspend fun getScienceClubTags() = remoteDataSource.getScienceClubTags().let {
        Resource(it.status, it.data?.map { tag -> tag.toDomain() }, it.message)
    }
}