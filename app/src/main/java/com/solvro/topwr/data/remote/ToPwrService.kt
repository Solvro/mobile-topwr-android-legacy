package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.endDate.EndDate
import retrofit2.Response
import retrofit2.http.GET


interface ToPwrService {
    /**
     * Get academic end of year
     * @return Response<EndDate> with academic year date info
     */
    @GET("academic-year-end-date")
    suspend fun getEndDate(): Response<EndDate>
}