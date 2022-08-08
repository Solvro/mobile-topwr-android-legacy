package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClubs.ScienceClub
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

    suspend fun getScientificCircles(): Resource<List<ScienceClub>> =
        getResult { service.getScientificCircles() }

    suspend fun getMaps(): Resource<List<Building>> = getResult { service.getMaps() }

    suspend fun getNotices(): Resource<List<Notices>> = getResult { service.getNotices() }

    suspend fun getWeekDayException(): Resource<WeekDayException> =
        getResult { service.getWeekDayException() }
}