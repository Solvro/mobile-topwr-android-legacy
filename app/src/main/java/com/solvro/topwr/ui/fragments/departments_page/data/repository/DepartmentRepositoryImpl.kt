package com.solvro.topwr.ui.fragments.departments_page.data.repository

import androidx.paging.PagingData
import com.solvro.topwr.data.local.DataStoreSource
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.ui.fragments.departments_page.domain.repository.DepartmentRepository
import com.solvro.topwr.utils.Resource
import kotlinx.coroutines.flow.Flow
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