package com.solvro.topwr.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    //get Academic end of year date
    // @return LiveData<Resource<EndDate>> with End Date info
    fun getEndDate() : LiveData<Resource<EndDate>> = liveData { emit(remoteDataSource.getEndDate()) }
}