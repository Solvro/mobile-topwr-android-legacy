package com.solvro.topwr.features.departments.data

import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments.domain.DepartmentsRepository
import com.solvro.topwr.core.api.model.Resource
import javax.inject.Inject

class DepartmentsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DepartmentsRepository {

    override suspend fun getDepartment(departmentNumber: Int): Resource<DepartmentsRemote?> {
        val resource = remoteDataSource.getDepartment(departmentNumber)
        return when (resource.status) {
            Resource.Status.ERROR -> Resource.error(resource.message!!)
            Resource.Status.SUCCESS -> Resource.success(resource.data?.firstOrNull())
            Resource.Status.LOADING -> Resource.loading()
        }
    }

    override fun getDepartmentsPaged() = remoteDataSource.getPagedDepartments()

    override suspend fun getScienceClubs(): Resource<List<ScienceClub>> =
        remoteDataSource.getScientificCircles()

    override fun getScienceClubsPaged() = remoteDataSource.getPagedScientificCircles()

    override suspend fun getScienceClubTags() = remoteDataSource.getScienceClubTags()
}