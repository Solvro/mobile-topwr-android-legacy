package com.solvro.topwr.features.scienceclub.domain

import androidx.paging.PagingData
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.core.domain.model.Resource
import com.solvro.topwr.features.scienceclub.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface ScienceClubRepository {
    suspend fun getScienceClubs(): Resource<List<ScienceClub>>

    fun getScienceClubsPaged(): Flow<PagingData<ScienceClub>>

    suspend fun getScienceClubTags(): Resource<List<Tag>>
}