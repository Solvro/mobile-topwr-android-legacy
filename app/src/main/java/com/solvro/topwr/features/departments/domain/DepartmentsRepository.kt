package com.solvro.topwr.features.departments.domain

import androidx.paging.PagingData
import com.solvro.topwr.core.api.model.departments.DepartmentsRemote
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.model.tag.TagRemote
import com.solvro.topwr.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DepartmentsRepository {
    suspend fun getScienceClubs(): Resource<List<ScienceClub>>
    fun getScienceClubsPaged() : Flow<PagingData<ScienceClub>>
    suspend fun getScienceClubTags() : Resource<List<TagRemote>>
    suspend fun getDepartment(departmentNumber: Int): Resource<DepartmentsRemote?>
    fun getDepartmentsPaged(): Flow<PagingData<DepartmentsRemote>>
}