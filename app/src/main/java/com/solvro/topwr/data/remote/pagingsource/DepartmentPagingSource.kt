package com.solvro.topwr.data.remote.pagingsource

import com.solvro.topwr.core.api.model.department.DepartmentRemote
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.core.api.model.Resource

class DepartmentPagingSource(
    private val remoteDataSource: RemoteDataSource
) : BaseToPWrPagingSource<DepartmentRemote>() {
    override suspend fun getPagedData(startIndex: Int, resultPerPage: Int): List<DepartmentRemote>? {
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