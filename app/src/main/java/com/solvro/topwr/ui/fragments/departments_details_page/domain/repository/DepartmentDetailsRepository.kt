package com.solvro.topwr.ui.fragments.departments_details_page.domain.repository

import androidx.paging.PagingData
import com.solvro.topwr.data.model.scienceClub.ScienceClub
import com.solvro.topwr.data.model.tag.TagRemote
import com.solvro.topwr.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DepartmentDetailsRepository {
    suspend fun getScienceClubs(): Resource<List<ScienceClub>>
    fun getScienceClubsPaged() : Flow<PagingData<ScienceClub>>
    suspend fun getScienceClubTags() : Resource<List<TagRemote>>
}