package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.departments.ScientificCircle
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.scientificCircles.ScientificCircles
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: ToPwrService
) : BaseDataSource() {


    //get Academic end of year
    suspend fun getEndDate() : Resource<EndDate> = getResult { service.getEndDate() }

    //get list of departments
    suspend fun getDepartments() : Resource<List<Departments>> = getResult { service.getDepartments() }

    suspend fun getScientificCircles() : Resource<List<ScientificCircles>> = getResult { service.getScientificCircles() }
}