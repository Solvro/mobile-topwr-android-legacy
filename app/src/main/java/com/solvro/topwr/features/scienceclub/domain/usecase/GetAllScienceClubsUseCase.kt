package com.solvro.topwr.features.scienceclub.domain.usecase

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.solvro.topwr.core.base.FlowUseCase
import com.solvro.topwr.features.scienceclub.domain.ScienceClubRepository
import com.solvro.topwr.features.scienceclub.domain.model.ScienceClub
import com.solvro.topwr.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class GetAllScienceClubsUseCase @Inject constructor(
    private val scienceClubRepository: ScienceClubRepository
) : FlowUseCase<GetAllScienceClubsParams, PagingData<ScienceClub>>() {

    private var scienceClubJob: Job? = null

    override suspend fun action(params: GetAllScienceClubsParams, scope: CoroutineScope): Flow<PagingData<ScienceClub>> {
        scienceClubJob?.cancel()
        delay(Constants.DEFAULT_DEBOUNCE_TIME_MS)
        return scienceClubRepository.getScienceClubsPaged()
            .cancellable()
            .cachedIn(scope)
            .transform {
                val filteredData = it.filter { scienceClub ->
                    if (params.tagFilter == null) true else scienceClub.isTaggedAs(params.tagFilter)
                }.filter { scienceClub ->
                    scienceClub.name.lowercase().contains(params.textFilter.lowercase())
                }
                emit(filteredData)
            }
    }
}

data class GetAllScienceClubsParams(
    val tagFilter: String?,
    val textFilter: String
)