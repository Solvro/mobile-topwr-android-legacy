package com.solvro.topwr.data.remote

import com.solvro.topwr.data.model.aboutUs.AboutUs
import com.solvro.topwr.core.api.model.department.DepartmentRemote
import com.solvro.topwr.data.model.endDate.EndDate
import com.solvro.topwr.data.model.endDate.WeekDayException
import com.solvro.topwr.data.model.info.Info
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
    suspend fun getDepartments(
        @Query("_start") startIndex: Int,
        @Query("_limit") limit: Int,
        @Query("_sort") sortPropertyName: String = "displayOrder"
    ): Response<List<DepartmentRemote>>

    @GET("departments/count")
    suspend fun getDepartmentsCount(): Response<Int>

    @GET("departments")
    suspend fun getDepartment(
        @Query("displayOrder") departmentNumber: Int
    ): Response<List<DepartmentRemote>>

    @GET("scientific-circles")
    suspend fun getScientificCircles(
        @Query("_start") startIndex: Int,
        @Query("_limit") limit: Int
    ): Response<List<ScienceClub>>

    @GET("scientific-circles/count")
    suspend fun getScientificCirclesCount(): Response<Int>

    @GET("tags")
    suspend fun getScientificCirclesTags(): Response<List<TagRemote>>

    @GET("maps")
    suspend fun getMaps(): Response<List<Building>>

    @GET("notices")
    suspend fun getNotices(): Response<List<Notices>>

    @GET("week-day-exceptions")
    suspend fun getWeekDayException(): Response<WeekDayException>

    @GET("infos")
    suspend fun getInfos(@Query("title_contains") name: String): Response<List<Info>>

    @GET("about-us")
    suspend fun getAboutUs(): Response<AboutUs>
}