package com.solvro.topwr.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.utils.Resource
import kotlin.math.ceil

class ScienceClubPagingSource(
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, ScienceClub>() {

    private var allResultsCount: Int? = null
    private val pagingError: LoadResult<Int, ScienceClub> = LoadResult.Error(
        Throwable("Load paged data error")
    )

    override fun getRefreshKey(state: PagingState<Int, ScienceClub>): Int {
        // Key where paging starts after refresh
        // 0 means that paged data will start from the beginning
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScienceClub> {
        val resultPerPage = params.loadSize

        getAllResultsCountIfNull()
        if (allResultsCount == null)
            return pagingError

        val currentPageNumber = params.key ?: 0
        val response = remoteDataSource.getScientificCircles(
            startIndex = getStartIndex(currentPageNumber, resultPerPage),
            limit = resultPerPage
        )
        if (response.status == Resource.Status.ERROR)
            return pagingError

        val prevPageNumber = getPrevPageNumber(currentPageNumber)
        val nextPageNumber = getNextPageNumber(currentPageNumber, resultPerPage)

        return LoadResult.Page(
            data = response.data ?: listOf(),
            prevKey = prevPageNumber,
            nextKey = nextPageNumber
        )
    }

    private suspend fun getAllResultsCountIfNull() {
        if (allResultsCount == null) {
            val allResultsCountResponse = remoteDataSource.getScientificCirclesCount()
            allResultsCount = allResultsCountResponse.data
        }
    }

    private fun getStartIndex(currentPageNumber: Int, resultPerPage: Int) =
        currentPageNumber.times(resultPerPage)

    private fun getPrevPageNumber(currentPageNumber: Int) =
        if (currentPageNumber > 0) currentPageNumber - 1 else null

    private fun getNextPageNumber(currentPageNumber: Int, resultPerPage: Int) =
        if (currentPageNumber < getLastPageNumber(resultPerPage)) currentPageNumber + 1 else null

    private fun getLastPageNumber(resultPerPage: Int): Int {
        if (allResultsCount == null) return 0
        return ceil(allResultsCount!!.toDouble() / resultPerPage.toDouble()).toInt()
    }
}