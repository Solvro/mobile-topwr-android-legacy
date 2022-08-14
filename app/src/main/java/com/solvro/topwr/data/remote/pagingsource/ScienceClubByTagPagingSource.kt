package com.solvro.topwr.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import kotlin.math.ceil
import kotlin.math.min

class ScienceClubByTagPagingSource(
    private val tag: String,
    private val remoteDataSource: RemoteDataSource
) : PagingSource<Int, ScienceClub>() {

    private val resultPerPage = 10
    private var allResultsCount: Int? = null

    override fun getRefreshKey(state: PagingState<Int, ScienceClub>): Int {
        // Key where paging starts after refresh
        // 0 means that paged data will start from the beginning
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScienceClub> {
        val currentPageNumber = params.key ?: 0
        val response = remoteDataSource.getScientificCircleTag(tag)

        if (allResultsCount == null)
            allResultsCount = response.data?.firstOrNull()?.scientific_circles?.size ?: 0

        val prevPageNumber =
            if (currentPageNumber > 0) currentPageNumber - 1 else null

        val nextPageNumber =
            if (currentPageNumber < getLastPageNumber()) currentPageNumber + 1 else null

        return LoadResult.Page(
            data = response.data?.firstOrNull()?.scientific_circles?.subList(
                (prevPageNumber ?: 0) * resultPerPage,
                min(currentPageNumber * (resultPerPage + 1), allResultsCount ?: 0)
            ) ?: listOf(),
            prevKey = prevPageNumber,
            nextKey = nextPageNumber
        )
    }

    private fun getLastPageNumber(): Int {
        if (allResultsCount == null) return 0
        return ceil(allResultsCount!!.toDouble() / resultPerPage.toDouble()).toInt()
    }
}