package com.solvro.topwr.data.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlin.math.ceil

abstract class BaseToPWrPagingSource<Value : Any> : PagingSource<Int, Value>() {

    private var allResultsCount: Int? = null
    private val pagingError: LoadResult<Int, Value> = LoadResult.Error(
        Throwable("Load paged data error")
    )

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        /* Key where paging starts after refresh
        0 means that paged data will start from the beginning */
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val resultPerPage = params.loadSize

        getAllResultsCountIfNull()
        if (allResultsCount == null)
            return pagingError

        val currentPageNumber = params.key ?: 0
        val startIndex = getStartIndex(currentPageNumber, resultPerPage)
        val pagedData =
            getPagedData(startIndex, resultPerPage) ?: return pagingError

        val prevPageNumber = getPrevPageNumber(currentPageNumber)
        val nextPageNumber = getNextPageNumber(currentPageNumber, resultPerPage)

        return LoadResult.Page(
            data = pagedData,
            prevKey = prevPageNumber,
            nextKey = nextPageNumber
        )
    }

    protected abstract suspend fun getPagedData(
        startIndex: Int,
        resultPerPage: Int
    ): List<Value>?

    protected abstract suspend fun getAllResultsCount(): Int?

    private suspend fun getAllResultsCountIfNull() {
        if (allResultsCount == null) {
            allResultsCount = getAllResultsCount()
        }
    }

    protected fun getStartIndex(currentPageNumber: Int, resultPerPage: Int) =
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