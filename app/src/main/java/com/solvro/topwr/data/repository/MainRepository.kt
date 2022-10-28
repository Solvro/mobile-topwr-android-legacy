package com.solvro.topwr.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.solvro.topwr.utils.Resource
import com.solvro.topwr.data.local.DataStoreSource
import com.solvro.topwr.data.model.aboutUs.AboutUs
import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.info.Info
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.remote.RemoteDataSource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreSource: DataStoreSource
) {
    //get Academic end of year date
    // @return LiveData<Resource<EndDate>> with End Date info
    fun getEndDate(): LiveData<Resource<EndDate>> =
        liveData { emit(remoteDataSource.getEndDate()) }

    //get list of departments
    // @return LiveData<Resource<List<Departments>>> with departments
    @Deprecated(
        "Use paged departments flow with getDepartmentsPaged() method",
        replaceWith = ReplaceWith("getDepartmentsPaged()")
    )
    fun getDepartments(): LiveData<Resource<List<Departments>>> =
        liveData {
            emit(Resource.loading(null))
            emit(remoteDataSource.getDepartments())
        }

    suspend fun getDepartment(departmentNumber: Int): Resource<Departments?> {
        val resource = remoteDataSource.getDepartment(departmentNumber)
        return when (resource.status) {
            Resource.Status.ERROR -> Resource.error(resource.message!!)
            Resource.Status.SUCCESS -> Resource.success(resource.data?.firstOrNull())
            Resource.Status.LOADING -> Resource.loading()
        }
    }

    fun getDepartmentsPaged() = remoteDataSource.getPagedDepartments()

    suspend fun getBuildings(): Resource<List<Building>> = remoteDataSource.getBuildings()

    fun getNotices(): LiveData<Resource<List<Notices>>> =
        liveData {
            emit(Resource.loading(null))
            emit(remoteDataSource.getNotices())
        }

    fun getWeekDayException(): LiveData<Resource<WeekDayException>> = liveData {
        emit(remoteDataSource.getWeekDayException())
    }

    fun addIdToBuildingsSearchHistory(id: Int) {
        dataStoreSource.addToBuildingsSearchHistory(id)
    }

    fun getBuildingsSearchHistory() = dataStoreSource.getBuildingsSearchHistory()

    suspend fun getInfos(name: String = ""): Resource<List<Info>> = remoteDataSource.getInfos(name)

    suspend fun getAboutUs(): Resource<AboutUs> = remoteDataSource.getAboutUs()
}