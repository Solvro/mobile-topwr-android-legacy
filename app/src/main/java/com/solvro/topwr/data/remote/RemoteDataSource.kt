package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ToPwrService
) : BaseDataSource() {


    //get Academic end of year
    suspend fun getEndDate() : Resource<EndDate> = getResult { service.getEndDate() }
}