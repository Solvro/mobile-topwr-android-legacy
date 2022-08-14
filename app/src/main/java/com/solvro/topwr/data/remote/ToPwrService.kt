package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.departments.Departments
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.maps.Building
import com.solvro.topwr.data.model.notices.Notices
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.model.tag.TagRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


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
    suspend fun getScientificCircles(
        @Query("_start") startIndex: Int,
        @Query("_limit") limit: Int
    ): Response<List<ScienceClub>>

    @GET("scientific-circles/count")
    suspend fun getScientificCirclesCount(): Response<Int>

    @GET("tags")
    suspend fun getScientificCirclesTags(): Response<List<TagRemote>>

    @GET("tags")
    suspend fun getScientificCirclesTag(
        @Query("name") tagName: String
    ): Response<List<TagRemote>>

    @GET("maps")
    suspend fun getMaps(): Response<List<Building>>

    @GET("notices")
    suspend fun getNotices(): Response<List<Notices>>

    @GET("week-day-exceptions")
    suspend fun getWeekDayException(): Response<WeekDayException>
}