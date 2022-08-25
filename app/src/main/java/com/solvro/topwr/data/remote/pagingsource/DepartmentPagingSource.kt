package com.solvro.topwr.data.remote.pagingsource

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.utils.Resource

class DepartmentPagingSource(
    private val remoteDataSource: RemoteDataSource
) : BaseToPWrPagingSource<Departments>() {
    override suspend fun getPagedData(startIndex: Int, resultPerPage: Int): List<Departments>? {
        val response = remoteDataSource.getDepartments(startIndex, resultPerPage)
        return if (response.status == Resource.Status.ERROR)
            null
        else response.data
    }

    override suspend fun getAllResultsCount(): Int? {
        val allResultsCountResponse = remoteDataSource.getDepartmentsCount()
        return allResultsCountResponse.data
    }
}