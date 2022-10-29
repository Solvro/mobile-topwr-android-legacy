package com.solvro.topwr.features.departments_details_page.data.repository

import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.departments_details_page.domain.repository.DepartmentDetailsRepository
import com.solvro.topwr.utils.Resource
import javax.inject.Inject

class DepartmentDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : DepartmentDetailsRepository {

    override suspend fun getScienceClubs(): Resource<List<ScienceClub>> =
        remoteDataSource.getScientificCircles()

    override fun getScienceClubsPaged() = remoteDataSource.getPagedScientificCircles()

    override suspend fun getScienceClubTags() = remoteDataSource.getScienceClubTags()
}