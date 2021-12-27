package com.solvro.topwr.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.maps.Maps
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClubs.ScienceClubs
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    //get Academic end of year date
    // @return LiveData<Resource<EndDate>> with End Date info
    fun getEndDate(): LiveData<Resource<EndDate>> =
        liveData { emit(remoteDataSource.getEndDate()) }

    //get list of departments
    // @return LiveData<Resource<List<Departments>>> with departments
    fun getDepartments(): LiveData<Resource<List<Departments>>> =
        liveData { emit(remoteDataSource.getDepartments()) }

    fun getScienceClubs(): LiveData<Resource<List<ScienceClubs>>> =
        liveData { emit(remoteDataSource.getScientificCircles()) }

    fun getMaps(): LiveData<Resource<List<Maps>>> = liveData { emit(remoteDataSource.getMaps()) }

    fun getNotices(): LiveData<Resource<List<Notices>>> =
        liveData { emit(remoteDataSource.getNotices()) }

    suspend fun getWeekDayException(): Resource<WeekDayException> =
        remoteDataSource.getWeekDayException()
}