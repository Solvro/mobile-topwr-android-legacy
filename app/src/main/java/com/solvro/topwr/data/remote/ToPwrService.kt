package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.departments.ScientificCircle
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.maps.Maps
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scientificCircles.ScientificCircles
import retrofit2.Response
import retrofit2.http.GET


interface ToPwrService {
    /**
     * Get academic end of year
     * @return Response<EndDate> with academic year date info
     */
    @GET("academic-year-end-date")
    suspend fun getEndDate(): Response<EndDate>

    /**
     * Get list of departments
     * @return Response<List<Departments>> with list of departments
     */
    @GET("departments")
    suspend fun getDepartments(): Response<List<Departments>>

    @GET("scientific-circles")
    suspend fun getScientificCircles() : Response<List<ScientificCircles>>

    @GET("maps")
    suspend fun getMaps() : Response<List<Maps>>

    @GET("notices")
    suspend fun getNotices() : Response<List<Notices>>
}