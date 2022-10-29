package com.solvro.topwr.data.remote.pagingsource

import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.utils.Resource

class DepartmentPagingSource(
    private val remoteDataSource: RemoteDataSource
) : BaseToPWrPagingSource<DepartmentsRemote>() {
    override suspend fun getPagedData(startIndex: Int, resultPerPage: Int): List<DepartmentsRemote>? {
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