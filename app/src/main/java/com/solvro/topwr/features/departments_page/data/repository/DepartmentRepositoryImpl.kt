package com.solvro.topwr.features.departments_page.data.repository

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments_page.domain.repository.DepartmentRepository
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DepartmentRepository {

    override suspend fun getDepartment(departmentNumber: Int): Resource<Departments?> {
        val resource = remoteDataSource.getDepartment(departmentNumber)
        return when (resource.status) {
            Resource.Status.ERROR -> Resource.error(resource.message!!)
            Resource.Status.SUCCESS -> Resource.success(resource.data?.firstOrNull())
            Resource.Status.LOADING -> Resource.loading()
        }
    }

    override fun getDepartmentsPaged() = remoteDataSource.getPagedDepartments()

}