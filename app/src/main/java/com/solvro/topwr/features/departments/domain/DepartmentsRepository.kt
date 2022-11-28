package com.solvro.topwr.features.departments.domain

import androidx.paging.PagingData
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.core.api.model.Resource
import com.solvro.topwr.features.departments.domain.model.Departments
import com.solvro.topwr.features.departments.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface DepartmentsRepository {
    suspend fun getScienceClubs(): Resource<List<ScienceClub>>
    fun getScienceClubsPaged() : Flow<PagingData<ScienceClub>>
    suspend fun getScienceClubTags() : Resource<List<Tag>>
    suspend fun getDepartment(departmentNumber: Int): Resource<Departments?>
    fun getDepartmentsPaged(): Flow<PagingData<Departments>>
}