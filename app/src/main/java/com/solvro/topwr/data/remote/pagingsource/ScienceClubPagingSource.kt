package com.solvro.topwr.data.remote.pagingsource

import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.core.api.model.Resource

class ScienceClubPagingSource(
    private val remoteDataSource: RemoteDataSource
) : BaseToPWrPagingSource<ScienceClub>() {

    override suspend fun getPagedData(
        startIndex: Int,
        resultPerPage: Int
    ): List<ScienceClub>? {
        val response = remoteDataSource.getScientificCircles(
            startIndex = startIndex,
            limit = resultPerPage
        )
        return if (response.status == Resource.Status.ERROR)
            null
        else response.data
    }

    override suspend fun getAllResultsCount(): Int? {
        val allResultsCountResponse = remoteDataSource.getScientificCirclesCount()
        return allResultsCountResponse.data
    }
}