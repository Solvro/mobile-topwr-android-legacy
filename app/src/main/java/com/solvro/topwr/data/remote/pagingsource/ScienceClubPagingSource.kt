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

    override fun getRefreshKey(state: PagingState<Int, ScienceClub>): Int {
        // Key where paging starts after refresh
        // 0 means that paged data will start from the beginning
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScienceClub> {
        val resultPerPage = params.loadSize
        if (allResultsCount == null) {
            val allResultsCountResponse = remoteDataSource.getScientificCirclesCount()
            if (allResultsCountResponse.status == Resource.Status.ERROR)
                return LoadResult.Error(
                    Throwable("Load paged data error")
                )
            allResultsCount = allResultsCountResponse.data
        }


        val currentPageNumber = params.key ?: 0
        val response = remoteDataSource.getScientificCircles(
            startIndex = params.key?.times(resultPerPage) ?: 0,
            limit = resultPerPage
        )
        if (response.status == Resource.Status.ERROR)
            return LoadResult.Error(
                Throwable("Load paged data error")
            )

        val prevPageNumber =
            if (currentPageNumber > 0) currentPageNumber - 1 else null

        val nextPageNumber =
            if (currentPageNumber < getLastPageNumber(resultPerPage)) currentPageNumber + 1 else null

        return LoadResult.Page(
            data = response.data ?: listOf(),
            prevKey = prevPageNumber,
            nextKey = nextPageNumber
        )
    }

    private fun getLastPageNumber(resultPerPage: Int): Int {
        if (allResultsCount == null) return 0
        return ceil(allResultsCount!!.toDouble() / resultPerPage.toDouble()).toInt()
    }
}