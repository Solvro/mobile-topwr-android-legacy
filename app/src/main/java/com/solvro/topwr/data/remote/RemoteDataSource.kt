package com.solvro.topwr.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.model.tag.TagRemote
import com.solvro.topwr.data.remote.pagingsource.ScienceClubPagingSource
import com.solvro.topwr.utils.Constants
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ToPwrService
) : BaseDataSource() {


    //get Academic end of year
    suspend fun getEndDate(): Resource<EndDate> = getResult { service.getEndDate() }

    //get list of departments
    suspend fun getDepartments(): Resource<List<Departments>> =
        getResult { service.getDepartments() }

    suspend fun getScientificCircles(
        startIndex: Int = 1,
        limit: Int = 20
    ): Resource<List<ScienceClub>> =
        getResult { service.getScientificCircles(startIndex = startIndex, limit = limit) }

    suspend fun getScientificCirclesCount(): Resource<Int> =
        getResult { service.getScientificCirclesCount() }

    fun getPagedScientificCircles() = Pager(
        PagingConfig(
            pageSize = Constants.DEFAULT_PAGE_SIZE,
            initialLoadSize = Constants.DEFAULT_PAGE_SIZE
        )
    ) {
        ScienceClubPagingSource(this)
    }.flow

    suspend fun getScienceClubTags(): Resource<List<TagRemote>> =
        getResult { service.getScientificCirclesTags() }

    suspend fun getMaps(): Resource<List<Building>> = getResult { service.getMaps() }

    suspend fun getNotices(): Resource<List<Notices>> = getResult { service.getNotices() }

    suspend fun getWeekDayException(): Resource<WeekDayException> =
        getResult { service.getWeekDayException() }
}