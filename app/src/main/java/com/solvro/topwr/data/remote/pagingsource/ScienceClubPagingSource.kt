package com.solvro.topwr.data.remote.pagingsource

import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.core.api.model.scienceclub.ScienceClubRemote
import com.solvro.topwr.data.remote.RemoteDataSource

class ScienceClubPagingSource(
    private val remoteDataSource: RemoteDataSource
) : BaseToPWrPagingSource<ScienceClubRemote>() {

    override suspend fun getPagedData(
        startIndex: Int,
        resultPerPage: Int
    ): List<ScienceClubRemote>? {
        val response = remoteDataSource.getScientificCircles(
            startIndex = startIndex,
            limit = resultPerPage
        )
        return if (response is Resource.Error)
            null
        else response.data
    }

    override suspend fun getAllResultsCount(): Int? {
        val allResultsCountResponse = remoteDataSource.getScientificCirclesCount()
        return allResultsCountResponse.data
    }
}