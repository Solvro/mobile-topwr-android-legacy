package com.solvro.topwr.features.scienceclub.data

import androidx.paging.PagingData
import androidx.paging.map
import com.solvro.topwr.utils.Resource
import com.solvro.topwr.data.remote.RemoteDataSource
import com.solvro.topwr.features.scienceclub.domain.ScienceClubRepository
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.features.scienceclub.domain.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScienceClubRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : ScienceClubRepository {
    override suspend fun getScienceClubs(): Resource<List<ScienceClub>> =
        remoteDataSource.getScientificCircles().map { list ->
            list.map { it.toDomain() }
        }

    override fun getScienceClubsPaged(): Flow<PagingData<ScienceClub>> =
        remoteDataSource.getPagedScientificCircles()
            .map { pagingData -> pagingData.map { it.toDomain() } }

    override suspend fun getScienceClubTags(): Resource<List<Tag>> =
        remoteDataSource.getScienceClubTags().map { listOfTags ->
            listOfTags.map {
                it.toDomain()
            }
        }
}